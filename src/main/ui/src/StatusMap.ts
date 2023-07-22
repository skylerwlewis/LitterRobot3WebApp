export const statusMap: {
  [key: string]: string,
} = {
  'RDY': 'Unit ready to be used',
  'CCP': 'Cleaning cycle in progress',
  'CCC': 'Cleaning cycle completed',
  'CSF': 'Cat sensor fault',
  'DF1': 'Drawer is full -- But it is able to cycle a few more times',
  'DF2': 'Drawer is full -- But it is still able to cycle a few more times',
  'CST': 'Cat sensor timing',
  'CSI': 'Cat sensor interrupt',
  'BR': 'Bonnet removed',
  'P': 'Unit is paused',
  'OFF': 'Unit is turned off',
  'SDF': 'Drawer is completely full and will not cycle',
  'DFS': 'Drawer is completely full and will not cycle',
}

export const timeFormatString = 'ddd YYYY-MM-DD h:mm:ss A';