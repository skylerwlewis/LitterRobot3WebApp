import {Box, IconButton, Menu, MenuItem, Typography} from "@mui/material"
import MenuIcon from '@mui/icons-material/Menu';
import {useNavigate} from "react-router-dom";
import React from "react";
import MenuLink from "./MenuLink";

interface MobileAppBarBoxProps {
  logoText: string,
  links: MenuLink[]
}

const MobileAppBarBox = ({ logoText, links }: MobileAppBarBoxProps) => {

  const navigate = useNavigate();

  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  return (
    <>
      <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
        <IconButton
          size="large"
          aria-label="button to open appbar menu"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleOpenNavMenu}
          color="inherit"
        >
          <MenuIcon />
        </IconButton>
        <Menu
          id="menu-appbar"
          anchorEl={anchorElNav}
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
          }}
          keepMounted
          transformOrigin={{
            vertical: 'top',
            horizontal: 'left',
          }}
          open={Boolean(anchorElNav)}
          onClose={handleCloseNavMenu}
          sx={{
            display: { xs: 'block', md: 'none' },
          }}
        >
          {links.map(link => {
            return (
              <MenuItem key={link.key} onClick={() => {
                navigate(link.url);
                handleCloseNavMenu();
              }}>
                <Typography textAlign="center">
                  {link.text}
                </Typography>
              </MenuItem>
            );
          })}
        </Menu>
      </Box>
      <Typography
        variant="h5"
        noWrap
        component="a"
        href=""
        sx={{
          mr: 2,
          display: { xs: 'flex', md: 'none' },
          flexGrow: 1,
          fontFamily: 'monospace',
          fontWeight: 700,
          letterSpacing: '.3rem',
          color: 'inherit',
          textDecoration: 'none',
        }}
      >
        {logoText}
      </Typography>
    </>
  )
}

export default MobileAppBarBox;