
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'TEMPORARY_CODE_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('TEMPORARY_CODE_MANAGEMENT','temporarycode.adminFeature.ManageCode.name',1,'jsp/admin/plugins/temporarycode/ManageTemporaryCodeConfigs.jsp','temporarycode.adminFeature.ManageCode.description',0,'temporarycode',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'TEMPORARY_CODE_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('TEMPORARY_CODE_MANAGEMENT',1);

