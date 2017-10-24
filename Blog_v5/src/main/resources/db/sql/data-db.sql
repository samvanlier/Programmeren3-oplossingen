--insert data
insert into user (name, password)
VALUES ('sam', 'sam');

insert into entry (subject, message, tijd_van_toevoeging, user_id)
  VALUES ('Naar de bakker geweest', 'Was wel leuk...', '2016-08-30 14:47:00',
    SELECT id from user where name = 'sam');
insert into entry (subject, message, tijd_van_toevoeging, user_id)
  VALUES ('Netflix gekeken', 'Moet je ook eens doen!', '2017-09-11 11:34:00',
    SELECT id FROM user where name = 'sam');
insert into entry (subject, message, tijd_van_toevoeging, user_id)
  VALUES ('Oefeningetjes Prog 3 gemaakt', 'Ging vlotjes', '2019-02-27 02:55:45',
    SELECT id FROM user where name = 'sam');