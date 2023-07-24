import {Box, Button, Toolbar, Typography} from '@mui/material'
import {useNavigate} from 'react-router-dom';
import * as React from 'react';
import {PropsWithChildren} from 'react';
import MenuLink from './MenuLink';
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";

interface MenuToolbarProps {
  logoText: string,
  links: MenuLink[],
  userLinks: MenuLink[]
}

const MenuToolbar = ({logoText, links, userLinks, children}: PropsWithChildren<MenuToolbarProps>) => {

  const navigate = useNavigate();

  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <Toolbar disableGutters>
      <Typography
        variant='h6'
        noWrap
        component='a'
        href='/'
        sx={{
          mr: 2,
          display: {xs: 'none', md: 'flex'},
          fontFamily: 'monospace',
          fontWeight: 700,
          letterSpacing: '.3rem',
          color: 'inherit',
          textDecoration: 'none',
        }}
      >
        {logoText}
      </Typography>

      {children}

      <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
        {links.map(link => {
          return (
            <Button
              key={link.key}
              onClick={() => {
                navigate(link.url)
              }}
              sx={{my: 2, color: 'white', display: 'block'}}
            >
              {link.text}
            </Button>
          );
        })}
      </Box>

      <Box sx={{flexGrow: 0}}>
        <Tooltip title="Open settings">
          <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
            <Avatar/>
          </IconButton>
        </Tooltip>
        <Menu
          sx={{mt: '45px'}}
          id="menu-appbar"
          anchorEl={anchorElUser}
          anchorOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          keepMounted
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          open={Boolean(anchorElUser)}
          onClose={handleCloseUserMenu}
        >
          {userLinks.map((link) => (
            <MenuItem key={link.key} onClick={() => {
              navigate(link.url);
              handleCloseUserMenu();
            }}>
              <Typography textAlign="center">{link.text}</Typography>
            </MenuItem>
          ))}
        </Menu>
      </Box>

    </Toolbar>
  )
}

export default MenuToolbar;