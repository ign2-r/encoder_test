# Hello

DIO 1 = leftEncoder A (Rev Robotics Through Bore Encoder Blue Dupont)<br/>
DIO 2 = leftEncoder B (Rev Robotics Through Bore Encoder Yellow Dupont)<br/>
DIO 5 = rightEncoder A (Rev Robotics Through Bore Encoder Blue Dupont)<br/>
DIO 6 = rightEncoder B (Rev Robotics Through Bore Encoder Yellow Dupont)<br/>
(Signal Wires oriented towarad the inside of the roboRIO, GND on the outside)

### OUTPUT snippet (upon enabling Autonomous):
...<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
Left: 	-0.33<br/>
Right: 	-0.615<br/>
...<br/>

^^^ Not as expected - values are not iterating as they should - at some points the values jump up >=200% rather than interating linearly
