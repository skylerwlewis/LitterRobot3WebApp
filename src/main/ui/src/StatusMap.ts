export const statusMap: {
  [key: string]: string,
} = {
  'RDY': 'Ready',
  'CCP': 'Clean Cycle In Progress',
  'CCC': 'Clean Cycle Complete',
  'CSF': 'Cat Sensor Fault',
  'DF1': 'Drawer Almost Full - 2 Cycles Left',
  'DF2': 'Drawer Almost Full - 1 Cycle Left',
  'CST': 'Cat Sensor Timing',
  'CSI': 'Cat Sensor Interrupted',
  'BR': 'Bonnet Removed',
  'P': 'Paused',
  'OFF': 'Off',
  'SDF': 'Drawer Full At Startup',
  'DFS': 'Drawer Full',
  'DHF': 'Dump + Home Position Fault',
  'DPF': 'Dump Position Fault',
  'EC': 'Empty Cycle',
  'HPF': 'Home Position Fault',
  'OFFLINE': 'Offline',
  'OTF': 'Over Torque Fault',
  'PD': 'Pinch Detect',
  'SCF': 'Cat Sensor Fault At Startup',
  'SPF': 'Pinch Detect At Startup'
}

export const timeFormatString = 'ddd YYYY-MM-DD h:mm:ss A';