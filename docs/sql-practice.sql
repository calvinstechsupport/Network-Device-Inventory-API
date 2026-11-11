-- Network Device Inventory: PostgreSQL practice queries
-- Run these against a local or demo database only.

-- 1. View every device (SELECT)
SELECT id, name, device_type, ip_address, location, status
FROM network_devices
ORDER BY name;

-- 2. Filter devices by status (WHERE)
SELECT *
FROM network_devices
WHERE status = 'ACTIVE';

-- 3. Count devices by type (GROUP BY + aggregate)
SELECT device_type, COUNT(*) AS device_count
FROM network_devices
GROUP BY device_type
ORDER BY device_count DESC;

-- 4. Search for a device by part of its name (LIKE)
SELECT *
FROM network_devices
WHERE LOWER(name) LIKE '%router%';

-- 5. Change a device's location (UPDATE)
-- Replace 1 with an ID that exists in your own test database.
UPDATE network_devices
SET location = 'Networking Lab'
WHERE id = 1;

-- 6. Verify the update
SELECT id, name, location
FROM network_devices
WHERE id = 1;

-- 7. Delete a demonstration record only after checking the ID (DELETE)
-- DELETE FROM network_devices WHERE id = 1;

-- 8. Table definition (schema inspection)
-- PostgreSQL psql: \d network_devices
