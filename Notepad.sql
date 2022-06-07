--Город, Край
select * from jc_country_struct where area_id like '__0000000000' and area_id <> '';

--Край 2 -> Область 1, Область 2
select * from jc_country_struct where area_id like '_2___0000000' and area_id <> '020000000000';

--Край 2 Область 1 -> Район 1, Район 2
select * from jc_country_struct where area_id like '02001___0000' and area_id <> '020010000000';

--Край 2 Область 1 Район 1 -> Поселение 1, Поселение 2
select * from jc_country_struct where area_id like '02001001____' and area_id <> '020010010000';

