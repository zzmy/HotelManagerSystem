/*==============================================================*/
/* Database name:  Database_1                                   */
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2015/1/11 10:02:33                           */

/*只有用合法的用户帐号才能访问Oracle数据库，于是本组创建一个用户，步骤如*/
/*1.创建用户前                                                    */
/*1)不登录方式启动：                                              */
/*①　在命令控制符窗口中键入sqlplus /nolog                        */
/*②　使用connect命令以sysdba身份连接数据库                       */
/*   sql>connect / as sysdba                                      */
/*③　修改用户名密码                                              */
/*alter user system identified by system                          */
/*④　使用system用户登录                                          */
/*    sql>conn system/system                                      */     
/*2)直接用system用户登录                                          */
/*①　sql>conn system/口令（该密码为你的数据库中system用户的口令）*/       /* 2.开始创建用户                                                 */ 
/* 1)创建一个名称为hoteladm的用户，其密码为123456                 */ 
/*==============================================================  */ 


sql>create user hoteladm identified by 123456;

create user hoteladm identified by 123456;
grant connect,resource to hoteladm;
commit;
