INSERT INTO menu_item_data (nombre, tipo_href, href, class_css, title, orden) 
	VALUES ('Generador de etiquetas', 1, 'admin.utils.barcode', 'fa fa-caret_right', NULL, NULL);
INSERT INTO menu_item (nombre, descripcion, menu_item_id, menu_item_data_id, principal, tipo_href, href, class_css, title, orden) 
	VALUES ('Generador de etiquetas', NULL, 49, 57, false, 1, 'admin.utils.barcode', 'fa fa-caret-right', NULL, 2);

update menu_item set orden=1 where id=50;
