/*==============================================================*/
/* Database name:  Database_1                                   */
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2015/1/11 10:02:33                            */
/*==============================================================*/


-- Type package declaration
create or replace package PDTypes  
as
    TYPE ref_cursor IS REF CURSOR;
end;
/

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/


drop trigger TB_ORDER_ITEM_SEQUENCE_TRIGGER
/

drop trigger TB_SORT_SEQUENCE_TRIGGER
/

drop trigger TB_USER_SEQUENCE_TRIGGER
/

alter table TB_MENU
   drop constraint FK_TB_MENU_REFERENCE_TB_SORT
/

alter table TB_ORDER_FORM
   drop constraint FK_TB_ORDER_REFERENCE_TB_DESK
/

alter table TB_ORDER_FORM
   drop constraint FK_TB_ORDER_REFERENCE_TB_USER
/

alter table TB_ORDER_ITEM
   drop constraint FK_TB_ORDER_REFERENCE_TB_ORDER
/

alter table TB_ORDER_ITEM
   drop constraint FK_TB_ORDER_REFERENCE_TB_MENU
/

drop table TB_DESK cascade constraints
/

drop table TB_MENU cascade constraints
/

drop table TB_ORDER_FORM cascade constraints
/

drop table TB_ORDER_ITEM cascade constraints
/

drop table TB_SORT cascade constraints
/

drop table TB_USER cascade constraints
/

drop sequence TB_ORDER_ITEM_SEQUENCE
/

drop sequence TB_SORT_SEQUENCE
/

drop sequence TB_USER_SEQUENCE
/

/*==============================================================*/
/* Database: "Database_1"                                       */
/*==============================================================*/

create sequence TB_ORDER_ITEM_SEQUENCE
increment by 1
start with 1
nocycle
 nocache
noorder
/

create sequence TB_SORT_SEQUENCE
increment by 1
start with 1
 maxvalue 9999
 minvalue 1
nocycle
 nocache
noorder
/

create sequence TB_USER_SEQUENCE
increment by 1
start with 1
nocycle
 nocache
noorder
/

/*==============================================================*/
/* Table: TB_DESK                                               */
/*==============================================================*/
create table TB_DESK  (
   NUM                  NUMBER(4)                       not null,
   SEATING              NUMBER(2)                       not null
      constraint CKC_SEATING_TB_DESK check (SEATING >= 0),
   constraint PK_TB_DESK primary key (NUM)
)
/

/*==============================================================*/
/* Table: TB_MENU                                               */
/*==============================================================*/
create table TB_MENU  (
   NUM                  NUMBER(8)                       not null,
   SORT_ID              NUMBER(4)                       not null,
   NAME                 CHAR(20)                        not null,
   CODE                 CHAR(20)                        not null,
   UNIT                 CHAR(20)                        not null,
   UNIT_PRICE           NUMBER(7,2)                     not null,
   STATE                CHAR(20)                       default '销售' not null,
   constraint PK_TB_MENU primary key (NUM)
)
/

/*==============================================================*/
/* Table: TB_ORDER_FORM                                         */
/*==============================================================*/
create table TB_ORDER_FORM  (
   NUM                  NUMBER(11)                      not null,
   DESK_NUM             NUMBER(4),
   DATETIME             DATE                           default SYSDATE not null,
   MONEY                NUMBER(7,2),
   USER_ID              NUMBER(4),
   constraint PK_TB_ORDER_FORM primary key (NUM)
)
/

/*==============================================================*/
/* Table: TB_ORDER_ITEM                                         */
/*==============================================================*/
create table TB_ORDER_ITEM  (
   ID                   NUMBER(12)                      not null,
   ORDER_FORM_NUM       NUMBER(11),
   MENU_NUM             NUMBER(8),
   AMOUNT               NUMBER(2),
   TOTAL                NUMBER(7,2),
   constraint PK_TB_ORDER_ITEM primary key (ID)
)
/

/*==============================================================*/
/* Table: TB_SORT                                               */
/*==============================================================*/
create table TB_SORT  (
   ID                   NUMBER(4)                       not null,
   NAME                 CHAR(20)                        not null,
   constraint PK_TB_SORT primary key (ID),
   constraint AK_KEY_2_TB_SORT unique (NAME)
)
/

/*==============================================================*/
/* Table: TB_USER                                               */
/*==============================================================*/
create table TB_USER  (
   ID                   NUMBER(12)                      not null,
   NAME                 VARCHAR(20)                        not null,
   SEX                  CHAR(2)                         not null,
   BIRTHDAY             DATE                            not null,
   ID_CARD              CHAR(18)                       
   constraint ID_CARD_RULE check (length(ID_CARD)=15 or length(ID_CARD)=18),
   PASSWORD             CHAR(32)                        not null,
   POWERLEVEL   NUMBER(1)   default 6 not null,
   FREEZE               CHAR(10)                       default '正常' not null,
   constraint PK_TB_USER primary key (ID),
   constraint AK_KEY_2_TB_USER unique (NAME)
)
/

alter table TB_MENU
   add constraint FK_TB_MENU_REFERENCE_TB_SORT foreign key (SORT_ID)
      references TB_SORT (ID)
/

alter table TB_ORDER_FORM
   add constraint FK_TB_ORDER_REFERENCE_TB_DESK foreign key (DESK_NUM)
      references TB_DESK (NUM)
      on delete set null
/

alter table TB_ORDER_FORM
   add constraint FK_TB_ORDER_REFERENCE_TB_USER foreign key (USER_ID)
      references TB_USER (ID)
      on delete set null
/

alter table TB_ORDER_ITEM
   add constraint FK_TB_ORDER_REFERENCE_TB_ORDER foreign key (ORDER_FORM_NUM)
      references TB_ORDER_FORM (NUM)
      on delete cascade
/

alter table TB_ORDER_ITEM
   add constraint FK_TB_ORDER_REFERENCE_TB_MENU foreign key (MENU_NUM)
      references TB_MENU (NUM)
/


create trigger TB_ORDER_ITEM_SEQUENCE_TRIGGER before insert
on TB_ORDER_ITEM for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence TB_ORDER_ITEM_SEQUENCE
    select TB_ORDER_ITEM_SEQUENCE.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger TB_SORT_SEQUENCE_TRIGGER before insert
on TB_SORT for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence TB_SORT_SEQUENCE
    select TB_SORT_SEQUENCE.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger TB_USER_SEQUENCE_TRIGGER before insert
on TB_USER for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence TB_USER_SEQUENCE
    select TB_USER_SEQUENCE.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

insert into tb_user (id, name, sex, birthday, id_card, password, powerlevel) 
    values (1, 'aaa', 'M', to_date('94-10-31','yy-mm-dd'), 
        '123456789012345', 'd9f6e636e369552839e7bb8057aeb8da', 2);
insert into tb_user (id, name, sex, birthday, id_card, password, powerlevel) 
    values (2, 'bbb', 'F', to_date('86-12-31','yy-mm-dd'), 
        '987654321012345', '003d81296cbed643579b24d1cf6c907c', 4);
insert into tb_user (id, name, sex, birthday, id_card, password, powerlevel) 
    values (2, 'ccc', 'F', to_date('86-12-31','yy-mm-dd'), 
        '987654321012345678', '57a8bdf56042b790fee5c0d54f33a746', 6);

set line 1000;
set serveroutput on;
begin
    dbms_output.put_line('tb_desk:');
end;
/
    select * from tb_desk;
begin
    dbms_output.put_line('tb_sort:');
end;
/
    select * from tb_sort;
begin
    dbms_output.put_line('tb_menu:');
end;
/
    select * from tb_menu;
begin
    dbms_output.put_line('tb_order_form:');
end;
/
    select * from tb_order_form;
begin
    dbms_output.put_line('tb_order_item:');
end;
/
    select * from tb_order_item;
begin
    dbms_output.put_line('tb_user:');
    dbms_output.put_line('密码默认为:帐号123');
end;
/
    select * from tb_user;

commit;
