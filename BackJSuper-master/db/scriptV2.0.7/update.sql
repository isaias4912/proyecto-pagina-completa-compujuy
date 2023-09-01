ALTER TABLE `configuracion` 
ADD COLUMN `backup_hab` BIT(1) NULL DEFAULT NULL AFTER `size_desc_ticket`,
ADD COLUMN `backup_cron` VARCHAR(250) NULL DEFAULT NULL AFTER `backup_hab`;

INSERT INTO menu_item_data (nombre, tipo_href, href, class_css, title, orden) 
VALUES ('Backup(Respaldo) de la BD', 1, 'admin.utils.backup', 'fa fa-caret-right', NULL, NULL);

INSERT INTO menu_item (nombre, descripcion, menu_item_id, menu_item_data_id, principal, tipo_href, href, class_css, title, orden) 
VALUES ('Backup(respaldo) de la BD', NULL, 32, 55, false, 1, 'admin.utils.backup', 'fa fa-caret-right', NULL, 4);

