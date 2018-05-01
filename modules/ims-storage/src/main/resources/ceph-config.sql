DELETE FROM sys_parameter WHERE id = 'a4c6d06d131e4c4880d96ede02099f6e';
DELETE FROM sys_parameter WHERE id = 'bc29c69ad828483f8c3c6d03a8afcc74';
DELETE FROM sys_parameter WHERE id = 'c31bd08172864c0483421db1e23c71d1';
DELETE FROM sys_parameter WHERE id = 'c1a271c80d454c3bb357b6e9fe3bfd7b';
DELETE FROM sys_parameter WHERE id = '1a1c1aa629e04f91a7b021d1ad0ec9a4';
DELETE FROM sys_parameter WHERE id = '56ee0d4d66814a7abeaa8d0df745b775';

INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('a4c6d06d131e4c4880d96ede02099f6e', 'app.com.spark.ims.storage.type', '文件存储类型(1:本地 2:ceph)', '1', '0', 'input', '', '一旦确定存储类型,后续需慎重修改类型,否则会导致原有附件查找不到', '', 287, '0', '1', '1');
INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('bc29c69ad828483f8c3c6d03a8afcc74', 'app.com.spark.ims.storage.ceph.bucket_name', '文件存储空间名称', 'node1', '0', 'input', '', '修改配置后需调用刷新接口进行刷新,接口没报错表示刷新成功。

刷新接口
http://ip:port/{projectName}/ceph/config/refresh

其中projectName为工程名,如ims-de-web

验证接口
http://ip:port/{projectName}/ceph/config/test/{bucketName}
bucketName替换成实际的空间名即可', '', 288, '0', '1', '1');
INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('c31bd08172864c0483421db1e23c71d1', 'app.com.spark.ims.storage.ceph.region', '文件存储区域', '', '0', 'input', '', '修改配置后需调用刷新接口进行刷新,接口没报错表示刷新成功。

刷新接口
http://ip:port/{projectName}/ceph/config/refresh

其中projectName为工程名,如ims-de-web

验证接口
http://ip:port/{projectName}/ceph/config/test/{bucketName}
bucketName替换成实际的空间名即可', '', 289, '0', '1', '1');
INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('c1a271c80d454c3bb357b6e9fe3bfd7b', 'app.com.spark.ims.storage.ceph.host', '文件存储访问地址(包含端口)', 'http://10.211.55.32:7480', '0', 'input', '', '修改配置后需调用刷新接口进行刷新,接口没报错表示刷新成功。

刷新接口
http://ip:port/{projectName}/ceph/config/refresh

其中projectName为工程名,如ims-de-web

验证接口
http://ip:port/{projectName}/ceph/config/test/{bucketName}
bucketName替换成实际的空间名即可', '', 290, '0', '1', '1');
INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('1a1c1aa629e04f91a7b021d1ad0ec9a4', 'app.com.spark.ims.storage.ceph.access_key', '文件存储的密钥ak', 'JLC9VCX537Q4I0EQZITU', '0', 'input', '', '修改配置后需调用刷新接口进行刷新,接口没报错表示刷新成功。

刷新接口
http://ip:port/{projectName}/ceph/config/refresh

其中projectName为工程名,如ims-de-web

验证接口
http://ip:port/{projectName}/ceph/config/test/{bucketName}
bucketName替换成实际的空间名即可', '', 291, '0', '1', '1');


INSERT INTO public.sys_parameter (id, code, name, value, type, display_type, regular, remark, unit, sort_order, is_public, is_edit, is_display) VALUES ('56ee0d4d66814a7abeaa8d0df745b775', 'app.com.spark.ims.storage.ceph.secret_key', '文件存储的密钥sk', 'lCBWGGMvCsEme7pvqdpyRuzY28Iyr7hYkFk2z6yV', '0', 'input', '', '修改配置后需调用刷新接口进行刷新,接口没报错表示刷新成功。

刷新接口
http://ip:port/{projectName}/ceph/config/refresh

其中projectName为工程名,如ims-de-web

验证接口
http://ip:port/{projectName}/ceph/config/test/{bucketName}
bucketName替换成实际的空间名即可', '', 292, '0', '1', '1');
