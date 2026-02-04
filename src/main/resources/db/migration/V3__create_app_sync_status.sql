CREATE TABLE app_sync_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    license_uname VARCHAR(255),
    last_cloud_sync DATETIME
);
