--Status
INSERT INTO Status (id, description) VALUES (1, 'IDLE');
INSERT INTO Status (id, description) VALUES (2, 'RUNNING');
INSERT INTO Status (id, description) VALUES (3, 'COMPLETED');

--Plot
INSERT INTO Plot (id, available_size, total_size) VALUES (1, 1000.0, 1000.0);
INSERT INTO Plot (id, available_size, total_size) VALUES (2, 2000.0, 2000.0);
INSERT INTO Plot (id, available_size, total_size) VALUES (3, 3000.0, 3000.0);
INSERT INTO Plot (id, available_size, total_size) VALUES (4, 4000.0, 4000.0);
INSERT INTO Plot (id, available_size, total_size) VALUES (5, 5000.0, 5000.0);

--Crop
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (1, 'Orange', 100.0, 100.0, 5, 1);
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (2, 'Tobacco', 200.0, 200.0, 4, 1);
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (3, 'Cotton', 300.0, 300.0, 6, 1);
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (4, 'Paddy', 100.0, 100.0, 5, 2);
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (5, 'Wheat', 200.0, 200.0, 4, 2);
INSERT INTO Crop (id, name, supply_required, crop_size, minimum_hours_needed, plot_id) VALUES (6, 'Sugarcane', 300.0, 300.0, 6, 2);

--Sensor
INSERT INTO Sensor (id, status_id, plot_id) VALUES (1, 1, 1);
INSERT INTO Sensor (id, status_id, plot_id) VALUES (2, 1, 2);
INSERT INTO Sensor (id, status_id, plot_id) VALUES (3, 1, 3);
INSERT INTO Sensor (id, status_id, plot_id) VALUES (4, 1, 4);
INSERT INTO Sensor (id, status_id, plot_id) VALUES (5, 1, 5);

--Irrigation_System
INSERT INTO Irrigation_System(id, description) VALUES (1, 'system one');
INSERT INTO Irrigation_System(id, description) VALUES (2, 'system two');
INSERT INTO Irrigation_System(id, description) VALUES (3, 'system three');
INSERT INTO Irrigation_System(id, description) VALUES (4, 'system four');
INSERT INTO Irrigation_System(id, description) VALUES (5, 'system five');