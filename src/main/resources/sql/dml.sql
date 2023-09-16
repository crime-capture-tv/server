INSERT INTO cctv.user_tb(user_no,name,id,password,email,phone_number,role)
VALUES (1, "곽두팔", "testId", "testPass","testEmail@gmail.com","010-1234-1234","MANAGER");
INSERT INTO cctv.store_tb(store_no, business_regist_number,store_address,store_name,store_phone_number,store_type,name)
VALUES (1, 1112233333, "가게 주소1", "아이스크림 가게1", "02-123-1234", "ICECREAM_SHOP", 1),
       (2, 1112233333, "가게 주소2", "아이스크림 가게2", "02-123-4321", "ICECREAM_SHOP", 1);
INSERT INTO cctv.crime_video_tb(crime_video_no,suspicion_video_path,highlight_video_path,crime_type,criminal_status)
VALUES (1, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\test.mp4", "", "절도", 0)