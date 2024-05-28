GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.account TO ssbd01mok;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.personal_data TO ssbd01mok;
GRANT SELECT ON TABLE public.role TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE public.account_role TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE public.password_history TO ssbd01mok;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.accountconfirmation TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE public.confirmation_reminder TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE credential_reset TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE change_my_password TO ssbd01mok;
GRANT SELECT, INSERT, DELETE ON TABLE change_email TO ssbd01mok;
GRANT SELECT  ON TABLE account_theme TO ssbd01mok;
GRANT SELECT,INSERT ON TABLE account_time_zone TO ssbd01mok;
GRANT SELECT  ON TABLE account_theme TO ssbd01auth;
GRANT SELECT ON TABLE account_time_zone TO ssbd01auth;
GRANT SELECT, INSERT, DELETE ON TABLE public.account_unlock TO ssbd01mok;
GRANT SELECT, INSERT ON TABLE public.account_history TO ssbd01mok;
GRANT SELECT, INSERT ON TABLE public.account_history_role TO ssbd01mok;
GRANT SELECT, INSERT ON TABLE public.personal_data_history TO ssbd01mok;

GRANT SELECT, INSERT, DELETE ON TABLE public.jwt_whitelist_token TO ssbd01auth;
GRANT SELECT, UPDATE ON TABLE public.account TO ssbd01auth;
GRANT SELECT ON TABLE public.role TO ssbd01auth;
GRANT SELECT ON TABLE public.account_role TO ssbd01auth;
GRANT SELECT ON TABLE public.personal_data TO ssbd01auth;
GRANT SELECT, INSERT ON TABLE public.account_history TO ssbd01auth;
GRANT SELECT, INSERT ON TABLE public.account_history_role TO ssbd01auth;
GRANT SELECT, INSERT ON TABLE public.personal_data_history TO ssbd01auth;


GRANT SELECT ON TABLE public.account TO ssbd01mow;

GRANT SELECT, INSERT, UPDATE ON TABLE public.event TO ssbd01mow;
GRANT SELECT, INSERT, UPDATE ON TABLE public.session TO ssbd01mow;

GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.location TO ssbd01mow;
GRANT SELECT, INSERT, DELETE ON TABLE public.room TO ssbd01mow;

GRANT SELECT, INSERT, UPDATE ON TABLE public.speaker TO ssbd01mow;

GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.ticket TO ssbd01mow;


CREATE INDEX account_role_account_idx ON account_role USING btree (account_id);
CREATE INDEX account_role_role_idx ON account_role USING btree (roles_id);
CREATE INDEX account_history_role_account_idx ON account_history_role USING btree (accounthistory_id);
CREATE INDEX account_history_role_role_idx ON account_history_role USING btree (roles_id);
CREATE INDEX account_history_account_id_idx ON account_history USING btree (account_id);
CREATE INDEX event_created_by_idx ON event USING btree (created_by);
CREATE INDEX event_updated_by_idx ON event USING btree (updated_by);
CREATE INDEX location_created_by_idx ON location USING btree (created_by);
CREATE INDEX location_updated_by_idx ON location USING btree (updated_by);
CREATE INDEX room_created_by_idx ON room USING btree (created_by);
CREATE INDEX room_location_id_idx ON room USING btree (location_id);
CREATE INDEX room_updated_by_idx ON room USING btree (updated_by);
CREATE INDEX session_created_by_idx ON session USING btree (created_by);
CREATE INDEX session_event_id_idx ON session USING btree (event_id);
CREATE INDEX session_room_id_idx ON session USING btree (room_id);
CREATE INDEX session_speaker_id_idx ON session USING btree (speaker_id);
CREATE INDEX session_updated_by_idx ON session USING btree (updated_by);
CREATE INDEX speaker_created_by_idx ON speaker USING btree (created_by);
CREATE INDEX speaker_updated_by_idx ON speaker USING btree (updated_by);
CREATE INDEX ticket_account_id_idx ON ticket USING btree (account_id);
CREATE INDEX ticket_created_by_idx ON ticket USING btree (created_by);
CREATE INDEX ticket_session_id_idx ON ticket USING btree (session_id);
CREATE INDEX ticket_updated_by_idx ON ticket USING btree (updated_by);

ALTER TABLE account_role ADD UNIQUE (account_id, roles_id);


INSERT INTO public.role (id, version, name) VALUES ('550e8400-e29b-41d4-a716-446655440000', 0, 'ROLE_ADMIN');
INSERT INTO public.role (id, version, name) VALUES ('4c90f86a-0d82-4c51-b72c-80e20949a3b9', 0, 'ROLE_MANAGER');
INSERT INTO public.role (id, version, name) VALUES ('cd8ab1c1-2431-4e28-88b5-fdd54de3d92a', 0, 'ROLE_PARTICIPANT');

INSERT INTO public.account_theme (id, version, theme) VALUES ('666e8400-e29b-41d4-a716-446655440000', 0, 'LIGHT');
INSERT INTO public.account_theme (id, version, theme) VALUES ('777e8400-e29b-41d4-a716-446655440000', 0, 'DARK');

INSERT INTO public.account_time_zone (id, version, timeZone) VALUES ('888e8400-e29b-41d4-a716-446655440000', 0, 'Europe/Warsaw');
INSERT INTO public.account_time_zone (id, version, timeZone) VALUES ('999e8400-e29b-41d4-a716-446655440000', 0, 'Europe/London');

INSERT INTO public.account (id,username,password,active,verified, nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1','testAdmin','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1', 'Pudzian', 'Admin', 'admin202401@proton.me', 1);
INSERT INTO public.account_role (account_id,roles_id) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1','550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.account_history (id,account_id,username,password,active,verified, nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1','8b25c94f-f10f-4285-8eb2-39ee1c4002f1','testAdmin','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1', 'Pudzian', 'Admin', 'admin202401@proton.me', 1);
INSERT INTO public.account_history_role (accounthistory_id,roles_id) VALUES ('8b25c94f-f10f-4285-8eb2-39ee1c4002f1','550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.password_history(version,account_id,id,password) VALUES (0,'8b25c94f-f10f-4285-8eb2-39ee1c4002f1','2f3e920d-8c7a-4a15-a4d7-1ae28e5930b6','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id,username,password,active,verified,nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','testParticipant','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6', 'Test', 'Participant', 'participant202401@proton.me', 1);
INSERT INTO public.account_role (account_id,roles_id) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id,account_id,username,password,active,verified,nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','testParticipant','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6', 'Test', 'Participant', 'participant202401@proton.me', 1);
INSERT INTO public.account_history_role (accounthistory_id,roles_id) VALUES ('a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history(version,account_id,id,password) VALUES (0,'a8816c75-e735-4d16-9f3e-7fcf3d0e7fe6','67f7f35b-03d9-45df-b2d6-81cb6d63113f','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id,username,password,active,verified,nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157','testManager','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157', 'Test', 'Manager', 'manager202401@proton.me', 1);
INSERT INTO public.account_role (account_id,roles_id) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157','4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.account_history (id,account_id,username,password,active,verified,nonlocked,failedloginattempts,version,created_at,action_type, language) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157','5454d58c-6ae2-4eee-8980-a49a1664f157','testManager','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y',true,true,true,0,0,'2021-01-01 00:00:00','CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157', 'Test', 'Manager', 'manager202401@proton.me', 1);
INSERT INTO public.account_history_role (accounthistory_id,roles_id) VALUES ('5454d58c-6ae2-4eee-8980-a49a1664f157','4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.password_history(version,account_id,id,password) VALUES (0,'5454d58c-6ae2-4eee-8980-a49a1664f157','b57d5a3f-aae0-4a3d-9315-1e5e16b1f28d','$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8981-a49a1664f158', 'MENwitoldgombrowicz12', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-01-15 10:23:45', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8981-a49a1664f158', 'Witold', 'Gombrowicz', 'witoldgombrowicz12@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8981-a49a1664f158', '4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('f1a8d5a3-1111-4aaa-aaaa-1a1a1a1a1a1a', '3e11-6ae2-4eee-8981-a49a1664f158', 'witoldgombrowicz12', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-01-15 10:23:45', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('f1a8d5a3-1111-4aaa-aaaa-1a1a1a1a1a1a', 'Witold', 'Gombrowicz', 'witoldgombrowicz12@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('f1a8d5a3-1111-4aaa-aaaa-1a1a1a1a1a1a', '4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8981-a49a1664f158', 'b57d5a3f-aae0-4a3d-9315-1e5e16b1f28e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8982-a49a1664f159', 'MENagnieszkaosiecka23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-20 14:56:32', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8982-a49a1664f159', 'Agnieszka', 'Osiecka', 'agnieszkaosiecka23@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8982-a49a1664f159', '4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('e2b8d5b4-2222-4bbb-bbbb-2b2b2b2b2b2b', '3e11-6ae2-4eee-8982-a49a1664f159', 'agnieszkaosiecka23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-20 14:56:32', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('e2b8d5b4-2222-4bbb-bbbb-2b2b2b2b2b2b', 'Agnieszka', 'Osiecka', 'agnieszkaosiecka23@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('e2b8d5b4-2222-4bbb-bbbb-2b2b2b2b2b2b', '4c90f86a-0d82-4c51-b72c-80e20949a3b9');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8982-a49a1664f159', 'c68e6b4d-bbbe-4c8d-9c2e-3c3d3d3d3d3d', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8983-a49a1664f160', 'ADMstanislawmrozek44', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-03-05 08:12:11', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8983-a49a1664f160', 'Stanisław', 'Mrożek', 'stanislawmrozek44@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8983-a49a1664f160', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('d3c8d6c5-3333-4ccc-cccc-3c3c3c3c3c3c', '3e11-6ae2-4eee-8983-a49a1664f160', 'stanislawmrozek44', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-03-05 08:12:11', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('d3c8d6c5-3333-4ccc-cccc-3c3c3c3c3c3c', 'Stanisław', 'Mrożek', 'stanislawmrozek44@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('d3c8d6c5-3333-4ccc-cccc-3c3c3c3c3c', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8983-a49a1664f160', 'd79f7a4e-cccc-4d9f-9c4f-4d4e4e4e4e4e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', 'ADMjeremiprzybora23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-17 16:45:23', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', 'Jeremi', 'Przybora', 'jeremiprzybora23@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('e4d9d7e6-4444-4ddd-dddd-4d4d4d4d4d4d', '3e11-6ae2-4eee-8984-a49a1664f161', 'jeremiprzybora23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-17 16:45:23', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('e4d9d7e6-4444-4ddd-dddd-4d4d4d4d4d4d', 'Jeremi', 'Przybora', 'jeremiprzybora23@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('e4d9d7e6-4444-4ddd-dddd-4d4d4d4d4d4d', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8984-a49a1664f161', 'e89e8b4f-dddd-4e9f-9d5f-5e5e5e5e5e5e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'stanislawlem51', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-05-23 12:34:56', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'Stanisław', 'Lem', 'stanislawlem51@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('f5e8e7f6-5555-4eee-eeee-5e5e5e5e5e5e', '3e11-6ae2-4eee-8985-a49a1664f162', 'stanislawlem51', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-05-23 12:34:56', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('f5e8e7f6-5555-4eee-eeee-5e5e5e5e5e5e', 'Stanisław', 'Lem', 'stanislawlem51@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('f5e8e7f6-5555-4eee-eeee-5e5e5e5e5e5e', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8985-a49a1664f162', 'fa9e9c6f-eeee-4f9f-9e6f-6f6f6f6f6f6f', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'konstantygalczynski124', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-10 09:28:44', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'Konstanty', 'Gałczyński', 'konstantygalczynski124@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('e6e8e9e7-6666-4fff-ffff-6f6f6f6f6f6f', '3e11-6ae2-4eee-8986-a49a1664f163', 'konstantygalczynski124', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-10 09:28:44', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('e6e8e9e7-6666-4fff-ffff-6f6f6f6f6f6f', 'Konstanty', 'Gałczyński', 'konstantygalczynski124@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('e6e8e9e7-6666-4fff-ffff-6f6f6f6f6f6f', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8986-a49a1664f163', 'eabf9d7e-ffff-4faf-9f7f-7f7f7f7f7f7f', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'halinaposwiatoswka12', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-20 18:55:12', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'Halina', 'Poświatowska', 'halinaposwiatoswka12@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('f7f9e8f6-7777-4ggg-gggg-7g7g7g7g7g7g', '3e11-6ae2-4eee-8987-a49a1664f164', 'halinaposwiatoswka12', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-02-20 18:55:12', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('f7f9e8f6-7777-4ggg-gggg-7g7g7g7g7g7g', 'Halina', 'Poświatowska', 'halinaposwiatoswka12@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('f7f9e8f6-7777-4ggg-gggg-7g7g7g7g7g7g', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8987-a49a1664f164', 'fbafae8g-gggg-4fag-9g8g-8g8g8g8g8g8g', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'aleksanderchwat31', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-01-25 21:43:54', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'Aleksander', 'Chwat', 'aleksanderchwat31@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('f8g9f7h6-8888-4hhh-hhhh-8h8h8h8h8h8h', '3e11-6ae2-4eee-8988-a49a1664f165', 'aleksanderchwat31', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-01-25 21:43:54', 'CREATE', 'ENGLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('f8g9f7h6-8888-4hhh-hhhh-8h8h8h8h8h8h', 'Aleksander', 'Chwat', 'aleksanderchwat31@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('f8g9f7h6-8888-4hhh-hhhh-8h8h8h8h8h8h', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8988-a49a1664f165', 'fcbgaf9h-hhhh-4fah-9h9h-9h9h9h9h9h9h', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', 'wandaRutkiewicz21', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-01 09:10:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', 'Wanda', 'Rutkiewicz', 'wandaRutkiewicz21@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8984-a49a1664f161', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('a1a8d5a3-4444-4ddd-dddd-4d4d4d4d4d4d', '3e11-6ae2-4eee-8984-a49a1664f161', 'wandaRutkiewicz21', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-01 09:10:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('a1a8d5a3-4444-4ddd-dddd-4d4d4d4d4d4d', 'Wanda', 'Rutkiewicz', 'wandaRutkiewicz21@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('a1a8d5a3-4444-4ddd-dddd-4d4d4d4d4d4d', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8984-a49a1664f161', 'e89f5b5e-cccc-4d9e-9e2e-5e5e5e5e5e5e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'jerzyKukuczka22', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-02 11:12:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'Jerzy', 'Kukuczka', 'jerzyKukuczka22@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8985-a49a1664f162', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('b2b8d5a4-5555-5eee-eeee-5e5e5e5e5e5e', '3e11-6ae2-4eee-8985-a49a1664f162', 'jerzyKukuczka22', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-02 11:12:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('b2b8d5a4-5555-5eee-eeee-5e5e5e5e5e5e', 'Jerzy', 'Kukuczka', 'jerzyKukuczka22@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('b2b8d5a4-5555-5eee-eeee-5e5e5e5e5e5e', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8985-a49a1664f162', 'f90f6b5e-dddd-4e9e-9e2e-6e6e6e6e6e6e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'krzysztofWielicki23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-03 13:14:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'Krzysztof', 'Wielicki', 'krzysztofWielicki23@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8986-a49a1664f163', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('c3c8d6b5-6666-6fff-ffff-6f6f6f6f6f6f', '3e11-6ae2-4eee-8986-a49a1664f163', 'krzysztofWielicki23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-03 13:14:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('c3c8d6b5-6666-6fff-ffff-6f6f6f6f6f6f', 'Krzysztof', 'Wielicki', 'krzysztofWielicki23@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('c3c8d6b5-6666-6fff-ffff-6f6f6f6f6f6f', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8986-a49a1664f163', 'g12g7c5e-eeee-4f9e-9e2e-7e7e7e7e7e7e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'wojciechKurtyka24', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-04 15:16:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'Wojciech', 'Kurtyka', 'wojciechKurtyka24@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8987-a49a1664f164', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('d4d9d7b6-7777-7aaa-aaaa-7a7a7a7a7a7a', '3e11-6ae2-4eee-8987-a49a1664f164', 'wojciechKurtyka24', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-04 15:16:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('d4d9d7b6-7777-7aaa-aaaa-7a7a7a7a7a7a', 'Wojciech', 'Kurtyka', 'wojciechKurtyka24@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('d4d9d7b6-7777-7aaa-aaaa-7a7a7a7a7a7a', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8987-a49a1664f164', 'h23h8d5e-ffff-5a9e-9e2e-8e8e8e8e8e8e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'piotrPustelnik25', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-05 17:18:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'Piotr', 'Pustelnik', 'piotrPustelnik25@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8988-a49a1664f165', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('e5e9d7b7-8888-8bbb-bbbb-8b8b8b8b8b8b', '3e11-6ae2-4eee-8988-a49a1664f165', 'piotrPustelnik25', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-05 17:18:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('e5e9d7b7-8888-8bbb-bbbb-8b8b8b8b8b8b', 'Piotr', 'Pustelnik', 'piotrPustelnik25@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('e5e9d7b7-8888-8bbb-bbbb-8b8b8b8b8b8b', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8988-a49a1664f165', 'i34i9e5e-gggg-6b9e-9e2e-9e9e9e9e9e9e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8989-a49a1664f166', 'adamBielecki27', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-06 19:20:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8989-a49a1664f166', 'Adam', 'Bielecki', 'adamBielecki27@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8989-a49a1664f166', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('f6f9d7b8-9999-9ccc-cccc-9c9c9c9c9c9c', '3e11-6ae2-4eee-8989-a49a1664f166', 'adamBielecki27', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-06 19:20:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('f6f9d7b8-9999-9ccc-cccc-9c9c9c9c9c9c', 'Adam', 'Bielecki', 'adamBielecki27@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('f6f9d7b8-9999-9ccc-cccc-9c9c9c9c9c9c', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8989-a49a1664f166', 'j45j0f5e-hhhh-7c9e-9e2e-0e0e0e0e0e0e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8990-a49a1664f167', 'maciejBerbeka27', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-07 21:22:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8990-a49a1664f167', 'Maciej', 'Berbeka', 'maciejBerbeka27@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8990-a49a1664f167', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('g7g9d8b9-aaaa-aaaa-dddd-dddddddddddd', '3e11-6ae2-4eee-8990-a49a1664f167', 'maciejBerbeka27', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-07 21:22:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('g7g9d8b9-aaaa-aaaa-dddd-dddddddddddd', 'Maciej', 'Berbeka', 'maciejBerbeka27@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('g7g9d8b9-aaaa-aaaa-dddd-dddddddddddd', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8990-a49a1664f167', 'k56k1g5e-iiii-8d9e-9e2e-1e1e1e1e1e1e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8991-a49a1664f168', 'andrzejZawada29', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-08 23:24:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8991-a49a1664f168', 'Andrzej', 'Zawada', 'andrzejZawada29@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8991-a49a1664f168', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('h8h9d9b0-bbbb-bbbb-eeee-eeeeeeeeeeee', '3e11-6ae2-4eee-8991-a49a1664f168', 'andrzejZawada29', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-08 23:24:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('h8h9d9b0-bbbb-bbbb-eeee-eeeeeeeeeeee', 'Andrzej', 'Zawada', 'andrzejZawada29@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('h8h9d9b0-bbbb-bbbb-eeee-eeeeeeeeeeee', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8991-a49a1664f168', 'l67l2h5e-jjjj-9e9e-9e2e-2e2e2e2e2e2e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8992-a49a1664f169', 'januszMajer31', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 01:26:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8992-a49a1664f169', 'Janusz', 'Majer', 'januszMajer31@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8992-a49a1664f169', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('i9i0d0c1-cccc-cccc-ffff-ffffffffffff', '3e11-6ae2-4eee-8992-a49a1664f169', 'januszMajer31', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 01:26:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('i9i0d0c1-cccc-cccc-ffff-ffffffffffff', 'Janusz', 'Majer', 'januszMajer31@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('i9i0d0c1-cccc-cccc-ffff-ffffffffffff', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8992-a49a1664f169', 'm78m3i5e-kkkk-ae9e-9e2e-3e3e3e3e3e3e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8993-a49a1664f170', 'arturHajzer32', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 03:28:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8993-a49a1664f170', 'Artur', 'Hajzer', 'arturHajzer32@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8993-a49a1664f170', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('j0j1d1c2-dddd-dddd-gggg-gggggggggggg', '3e11-6ae2-4eee-8993-a49a1664f170', 'arturHajzer32', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 03:28:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('j0j1d1c2-dddd-dddd-gggg-gggggggggggg', 'Artur', 'Hajzer', 'arturHajzer32@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('j0j1d1c2-dddd-dddd-gggg-gggggggggggg', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8993-a49a1664f170', 'n89n4i5e-llll-be9e-9e2e-4e4e4e4e4e4e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8994-a49a1664f171', 'tadeuszPiotrowski33', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 05:30:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8994-a49a1664f171', 'Tadeusz', 'Piotrowski', 'tadeuszPiotrowski33@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8994-a49a1664f171', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('k1k2d2c3-eeee-eeee-hhhh-hhhhhhhhhhhh', '3e11-6ae2-4eee-8994-a49a1664f171', 'tadeuszPiotrowski33', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 05:30:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('k1k2d2c3-eeee-eeee-hhhh-hhhhhhhhhhhh', 'Tadeusz', 'Piotrowski', 'tadeuszPiotrowski33@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('k1k2d2c3-eeee-eeee-hhhh-hhhhhhhhhhhh', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8994-a49a1664f171', 'o90o5i5e-mmmm-ce9e-9e2e-5e5e5e5e5e5e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8995-a49a1664f172', 'eugeniuszChrobak34', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 07:32:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8995-a49a1664f172', 'Eugeniusz', 'Chrobak', 'eugeniuszChrobak34@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8995-a49a1664f172', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('p1p2d2c4-ffff-ffff-iiii-iiiiiiiiiiii', '3e11-6ae2-4eee-8995-a49a1664f172', 'eugeniuszChrobak34', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 07:32:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('p1p2d2c4-ffff-ffff-iiii-iiiiiiiiiiii', 'Eugeniusz', 'Chrobak', 'eugeniuszChrobak34@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('p1p2d2c4-ffff-ffff-iiii-iiiiiiiiiiii', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8995-a49a1664f172', 'p65p5i6e-nnnn-de9e-9e2e-6e6e6e6e6e6e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8996-a49a1664f173', 'marcinKaczkan21', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 09:34:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8996-a49a1664f173', 'Marcin', 'Kaczkan', 'marcinKaczkan21@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8996-a49a1664f173', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('n1n2d2c5-gggg-gggg-jjjj-jjjjjjjjjjjj', '3e11-6ae2-4eee-8996-a49a1664f173', 'marcinKaczkan21', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 09:34:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('n1n2d2c5-gggg-gggg-jjjj-jjjjjjjjjjjj', 'Marcin', 'Kaczkan', 'marcinKaczkan21@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('n1n2d2c5-gggg-gggg-jjjj-jjjjjjjjjjjj', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8996-a49a1664f173', 'f89f5i7e-oooo-ee9e-9e2e-7e7e7e7e7e7e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8997-a49a1664f174', 'piotrMorawski22', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 11:36:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8997-a49a1664f174', 'Piotr', 'Morawski', 'piotrMorawski22@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8997-a49a1664f174', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('m1m2d2c6-hhhh-hhhh-kkkk-kkkkkkkkkkkk', '3e11-6ae2-4eee-8997-a49a1664f174', 'piotrMorawski22', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 11:36:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('m1m2d2c6-hhhh-hhhh-kkkk-kkkkkkkkkkkk', 'Piotr', 'Morawski', 'piotrMorawski22@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('m1m2d2c6-hhhh-hhhh-kkkk-kkkkkkkkkkkk', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8997-a49a1664f174', 'g89g5i8e-pppp-fe9e-9e2e-8e8e8e8e8e8e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');

INSERT INTO public.account (id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('3e11-6ae2-4eee-8998-a49a1664f175', 'jacekTeler23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 13:38:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data (account_id, firstname, lastname, email, gender) VALUES ('3e11-6ae2-4eee-8998-a49a1664f175', 'Jacek', 'Teler', 'jacekTeler23@int.pl', 1);
INSERT INTO public.account_role (account_id, roles_id) VALUES ('3e11-6ae2-4eee-8998-a49a1664f175', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.account_history (id, account_id, username, password, active, verified, nonlocked, failedloginattempts, version, created_at, action_type, language) VALUES ('q1q2d2c7-jjjj-jjjj-llll-llllllllllll', '3e11-6ae2-4eee-8998-a49a1664f175', 'jacekTeler23', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y', true, true, true, 0, 0, '2024-04-09 13:38:00', 'CREATE', 'POLISH');
INSERT INTO public.personal_data_history (account_history_id, firstname, lastname, email, gender) VALUES ('q1q2d2c7-jjjj-jjjj-llll-llllllllllll', 'Jacek', 'Teler', 'jacekTeler23@int.pl', 1);
INSERT INTO public.account_history_role (accounthistory_id, roles_id) VALUES ('q1q2d2c7-jjjj-jjjj-llll-llllllllllll', 'cd8ab1c1-2431-4e28-88b5-fdd54de3d92a');
INSERT INTO public.password_history (version, account_id, id, password) VALUES (0, '3e11-6ae2-4eee-8998-a49a1664f175', 'h89h5i9e-qqqq-ge9e-9e2e-9e9e9e9e9e9e', '$2a$10$cZM2GhvetO6fZur/9s26P.alLI.bZmSWfxsrrsLWw4qHlD6F3903y');
