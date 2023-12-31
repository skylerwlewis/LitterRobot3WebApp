import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Container from '@mui/material/Container';
import MenuToolbar from './MenuToolbar';
import MobileAppBarBox from './MobileAppBarBox';
import MenuLink from './MenuLink';

const logoText = 'LR3';

const links: MenuLink[] = [
  {
    key: 'robots',
    text: 'Robots',
    url: '/'
  }
]

const userLinks: MenuLink[] = [
  {
    key: 'user',
    text: 'User',
    url: '/user'
  },
  {
    key: 'devices',
    text: 'Devices',
    url: '/devices'
  },
  {
    key: 'settings',
    text: 'Settings',
    url: '/settings'
  }
]

const ResponsiveAppBar = () => {

  return (
    <AppBar position='static'>
      <Container maxWidth='xl'>
        <MenuToolbar logoText={logoText} links={links} userLinks={userLinks}>
          <MobileAppBarBox logoText={logoText} links={links}/>
        </MenuToolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;