import {Box, Button, Toolbar, Typography} from '@mui/material'
import {useNavigate} from 'react-router-dom';
import {PropsWithChildren} from 'react';
import MenuLink from './MenuLink';

interface MenuToolbarProps {
  logoText: string,
  links: MenuLink[]
}

const MenuToolbar = ({logoText, links, children}: PropsWithChildren<MenuToolbarProps>) => {

  const navigate = useNavigate();

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
    </Toolbar>
  )
}

export default MenuToolbar;