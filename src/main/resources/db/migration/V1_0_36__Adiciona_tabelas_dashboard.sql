create sequence plataforma.seq_dashboard increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create sequence plataforma.seq_dashlet increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table plataforma.dashboard (seq_dashboard bigint not null, nom_dashboard varchar2(100) not null, constraint pk_dashboard primary key (seq_dashboard));

create table plataforma.dashlet (seq_dashlet bigint not null, nom_dashlet varchar2(100) not null, dsc_dashlet varchar2(100) not null, constraint pk_dashlet primary key (seq_dashlet));
alter table plataforma.dashlet add constraint uk_dash_nom_dashlet unique(nom_dashlet);

create table plataforma.dashboard_dashlet (seq_dashboard bigint not null, seq_dashlet bigint not null, constraint pk_dashboard_dashlet primary key (seq_dashboard, seq_dashlet));