
/*employee   备注：员工号默认从1开始*/

/*                    emp_id   emp_no    emp_name   emp_tel_num       emp_addr       emp_email   */
insert employee values(null,   '1',      'em1',    '18888888888',     'xian',        'xxx@xxx.com');
insert employee values(null,   '2',      'em2',    '18888888888',     'beijing',     'xxx@xxx.com');

/*sale       备注：对应每一次结帐，就会生成一个销售单  外键：员工*/
/*每一个结帐信息上会有员工emp的信息*/

/*                sale_ID    emp_id     sale_time         sale_payment   sale_change   sale_type(1:销售单 -1：退款单)    sale_status(0:代付款 1：已付款)*/
insert sale values(null,     '1',      '2016-06-14',      '50',         '6',         '1',                            '1');
insert sale values(null,     '2',      '2016-06-14',      '50',          '17',          '1',                            '1');
 
/*studio    备注：默认创建演出厅时候就会创建座位，因此studio_flag全部为1 */

/*                  studio_id   studio.name    studio_row_count  studio_col_count        studio_introduction       studio_flag   */
insert studio values(null,     '1号厅',        '2',              '2',                  'This is first hall',         '1');
insert studio values(null,     '2号厅',        '2',              '2',                  'This is second hall',        '1');


/*data_dict  备注 ： data_dict 是一个递归表，dict_parent_id references data_dict(dict_id) */


/* data_dict  1：根结点   2:影片类型,  3：语言类型  4：影片失效时间 
*             5:喜剧 ......  14:剧情
/*            15:汉语 ...     19:法语

/*                      dict_id    dict_parent_id   dict_index        dict_name      dict_value */
insert data_dict values('1',       '1'       ,      '1'     ,         '根',           '0');

insert data_dict values('2',       '1'       ,      '1'     ,         '影片类型',         '0');
insert data_dict values('3',       '1'       ,      '1'     ,         '语言类型',         '0');
insert data_dict values('4',       '1'       ,      '1'     ,         '买票失效时间',      '0');


insert data_dict values('5',       '2'       ,      '1'     ,         '喜剧',         '0');
insert data_dict values('6',       '2'       ,      '2'     ,         '恐怖',         '0');
insert data_dict values('7',       '2'       ,      '3'     ,         '爱情',         '0');
insert data_dict values('8',       '2'       ,      '4'     ,         '动作',         '0');
insert data_dict values('9',       '2'       ,      '5'     ,         '科幻',         '0');
insert data_dict values('10',      '2'       ,      '6'     ,         '武侠',         '0');
insert data_dict values('11',      '2'       ,      '7'     ,         '战争',         '0');
insert data_dict values('12',      '2'       ,      '8'     ,         '犯罪',         '0');
insert data_dict values('13',      '2'       ,      '9'     ,         '惊悚',         '0');
insert data_dict values('14',      '2'       ,      '10'     ,        '剧情',         '0');


insert data_dict values('15',       '3'       ,      '1'     ,         '汉语',         '0');
insert data_dict values('16',       '3'       ,      '2'     ,         '英语',         '0');
insert data_dict values('17',       '3'       ,      '3'     ,         '日语',         '0');
insert data_dict values('18',       '3'       ,      '4'     ,         '韩语',         '0');
insert data_dict values('19',       '3'       ,      '5'     ,         '法语',         '0');

insert data_dict values('20',       '4'       ,      '1'     ,          '1D' ,        '0');



update data_dict set dict_parent_id = NULL where dict_id =1;
/*play   备注： play_type_id 和 play_lang_id 是data_dict中的外键，它们的值参考上面定义*/
/* play_status 表示电影的状态  0：待安排 1：已安排  -1：下线 */

/*                 play_id  play_type_id   play_lang_id  play_name  play_introduction  play_image  play_length play_ticket_price  play_status*/
insert play values (null,   '8'      ,      '16'      ,       '魔兽'    ,'艾泽拉斯与德拉诺本是两颗祥和安宁的星球，人类在艾泽拉斯大陆上世代繁衍生息，兽人则在德拉诺的土地上辛勤耕耘。直到兽人古尔丹（吴彦祖 饰）的出现，打破了两个星球的和平。邪恶的古尔丹为了一己私利，使用恶魔能量打开了一扇传送门——黑暗之门，并残忍的破坏兽人的家园，将原本爱好和平的兽人变成一个个野蛮残暴的战士，迫使他们穿过黑暗之门到达人类的领地作恶。' , '魔兽.png' ,'124' , '22', '0');
insert play values (null,   '8'      ,      '16'       ,      'X战警'    ,'“天启”是漫威X战警世界里，能力最强大并且是史上首位变种人，自人类文明开始以来，便为世人当作天神来膜拜，他汲取多位变种人的超能力，演变成一位永生不死且无人能敌的超级变种人，历经数千年后，他再度觉醒，集结了多位强大的变种人意欲毁灭一切，重建世界秩序，当中包含失志的万磁王（迈克尔·法斯宾德 Michael Fassbender 饰）。当地球面临未知的巨大危机之际，瑞雯（詹妮弗·劳伦斯 Jennifer Lawrence 饰）在X教授（詹姆斯·麦卡沃伊 James McAvoy 饰）的协助之下，必须领导年轻的X战警们力抗这史上最强大的敌人，并扭转人类命运。' , 'X战警.png' ,'144' , '27', '0');
insert play values (null,   '7'    ,        '15'      ,       '北京遇上西雅图之不二情书'    ,'焦姣（汤唯 饰）15岁就随父亲移民到澳门，从此在赌城安家，并成为赌场公关。罗大牛（吴秀波 饰）则生活在洛杉矶，是一位房地产经纪人。姣爷和大牛通过一本书，巧合地通过手写书信的方式互相联系，两人从最初的完全陌生，到一步步敞开自己的心扉，逐渐进行心灵的交流，再到最后爱上对方。' , '北京遇上西雅图之不二情书.png' ,'131' , '33', '0');
insert play values (null,   '14'    ,        '15'       ,      '百鸟朝凤'    ,'老一代唢呐艺人焦三爷（陶泽如 饰）是个外冷内热的老人，看起来严肃古板，其实心怀热血。影片表现了在社会变革、民心浮躁的年代里，新老两代唢呐艺人为了信念的坚守所产生的真挚的师徒情、父子情、兄弟情。 ' , '百鸟朝凤.png' ,'108' , '23', '0');


/*seat  备注：seat_status 0:空位置 1:好座位  -1:座位损坏 */

/*                 seat_id      studio_id     seat_row    seat_col    seat_status*/
insert seat values(null,         '1' ,        '1',        '1',          '1' );
insert seat values(null,         '1' ,        '1',        '2',          '1' );
insert seat values(null,         '1' ,        '2',        '1',          '1' );
insert seat values(null,         '1' ,        '2',        '2',          '1' );
insert seat values(null,         '2' ,        '1',        '1',          '1' );
insert seat values(null,         '2' ,        '1',        '2',          '1' );
insert seat values(null,         '2' ,        '2',        '1',          '1' );
insert seat values(null,         '2' ,        '2',        '2',          '1' );


/*schedule   备注：sched_ticket_price 我采用的是默认的price*/

/*                    sched_id      studio_id     play_id        sched_time            sched_ticket_price  */
insert schedule values(null,         '1',         '1'           ,'2016-06-15 08:00',               '22');
insert schedule values(null,         '2',         '3'           ,'2016-06-15 08:00',               '33');

insert schedule values(null,         '1',         '4'           ,'2016-06-15 14:00',               '23');
insert schedule values(null,         '1',         '4'           ,'2016-06-15 16:00',               '23');
insert schedule values(null,         '2',         '4'           ,'2016-06-15 14:00',               '23');

insert schedule values(null,         '1',         '2'           ,'2016-06-15 18:00',               '27');
insert schedule values(null,         '1',         '2'           ,'2016-06-15 20:00',               '27');
insert schedule values(null,         '1',         '2'           ,'2016-06-15 22:00',               '27');
insert schedule values(null,         '2',         '2'           ,'2016-06-15 16:00',               '27');



/*ticket    ticket_status 0：待销售 1：锁定  9:卖出*/

/*                   ticket_id    seat_id       sched_id     ticket_price   ticket_status   ticket_locked_time*/ 
insert ticket values(null ,       '1'     ,     '1'    ,     '22'           ,'0' ,         '2016-06-16');
insert ticket values(null ,       '2'     ,     '1'    ,     '22'           ,'0' ,         '2016-06-16');
insert ticket values(null ,       '7'     ,     '2'    ,     '33'           ,'0' ,         '2016-06-16');
insert ticket values(null ,       '8'     ,     '2'    ,     '33'           ,'0' ,         '2016-06-16');



/*sale_item       备注： 每一个发票也就是一个sale_ID上可以对应多个ticket_id 比如一次购买两张票  */


/*                     sale_item_id      ticket_id      sale_ID       sale_item_price  */
insert sale_item values(null,            '1'     ,      '1'      ,    '22'     );
insert sale_item values(null,            '2'     ,      '1'      ,    '22'     );
insert sale_item values(null,            '3'     ,      '2'       ,   '33'     );




