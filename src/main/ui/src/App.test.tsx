import React from 'react';
import {render, screen} from '@testing-library/react';
import App from './App';

test('renders robots link', () => {
  render(<App/>);
  const robotsLinks = screen.getAllByText(/Robots/i)[0];
  expect(robotsLinks).toBeInTheDocument();
});
