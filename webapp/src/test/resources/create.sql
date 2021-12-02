drop table if exists `tbl_personen`;
CREATE TABLE `tbl_personen` (
        `id` varchar(36) NOT NULL,
        `nachname` varchar(30) NOT NULL,
        `vorname` varchar(30) DEFAULT NULL,
        PRIMARY KEY (`id`)
) ;