INSERT INTO cctv.user_tb(user_no,name,id,password,email,phone_number,role)
VALUES (1, "곽두팔", "test", "$2a$10$0T/vvPEYSIBXz3roX59oTeDW9tNpqP8z4b0WEhm0bSpP0jQmj9Yta","testEmail@gmail.com","010-1234-1234","MANAGER");
INSERT INTO cctv.store_tb(store_no, business_regist_number,store_name,store_phone_number,store_type,user_no)
VALUES (1, 1112233333,  "아이스크림 가게1", "02-123-1234", "ICECREAM_SHOP", 1),
       (2, 1112233333, "아이스크림 가게2", "02-123-4321", "ICECREAM_SHOP", 1);
INSERT INTO cctv.crime_video_tb(crime_video_no,suspicion_video_path_01,suspicion_video_path_02,highlight_video_path,recorded_at, crime_type,criminal_status,store_no)
VALUES (1, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 0,1),
       (2, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 0,1),
       (3, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 1,1),
       (4, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 1,1),
       (5, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 2,1),
       (6, "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-01\\test1.mp4", "\\\\192.168.0.42\\crimecapturetv\\suspicion-video\\cctv-02\\test2.mp4", "\\\\192.168.0.42\\crimecapturetv\\hilight-video\\test3.mp4", "2023-09-20T12:34:56.789", "절도", 2,1)