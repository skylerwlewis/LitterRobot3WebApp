import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Container from '@mui/material/Container';
import MenuToolbar from './MenuToolbar';
import MobileAppBarBox from './MobileAppBarBox';
import MenuLink from './MenuLink';

const logoText = 'LR';

const links: MenuLink[] = [
  {
    key: 'robot',
    text: 'Robot',
    url: '/'
  },
  {
    key: 'activity',
    text: 'Activity',
    url: '/activity'
  },
  {
    key: 'insights',
    text: 'Insights',
    url: '/insights'
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
        <MenuToolbar logoText={logoText} links={links} >
          <MobileAppBarBox logoText={logoText} links={links} />
        </MenuToolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;