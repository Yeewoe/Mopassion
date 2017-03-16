package org.yeewoe.mopanet.client;

import java.util.HashMap;

/**
 * 错误码
 * Created by wyw on 2016/3/22.
 */
public enum MopaErrno {
    LOCAL_MEMORY_LACK							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 1)		),	//内存分配不够
    LOCAL_CONNECT_FAILED                        (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 2)		),	//连接失败
    LOCAL_CALL_TIMEOUT							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 3)		),	//请求超时
    LOCAL_NETWORK_UNAVAILABLE					(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 4)		),	//网络不可用(无网络或还未与Server建立连接)
    LOCAL_NETWORK_ERROR							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 5)		),	//网络发送或接收过程中发生错误
    LOCAL_NOT_LOGIN							    (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 6)		),	//账号未登陆
    LOCAL_AUTH_FAILED							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 7)		),	//账号认证失败
    LOCAL_INVAILD_OPER							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 8)		),	//非法的操作调用
    LOCAL_NO_MESSAGE							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 9)		),	//暂时没有message(非阻塞模式获取消息可能返回此结果)
    LOCAL_NET_ERROR							    (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 10)		),	//本地网络状态
    LOCAL_JNI_OBJECT_PARSE_ERROR				(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 11)		),	//object parse to Java object error
    LOCAL_SQLEXCEPTION							(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 12)		),	//SQL Exception
    LOCAL_THREAD_INTERRUPTED					(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 13)		),	//Thread InterruptedException
    LOCAL_BYTE_ARRAY_INPUTSTREAM_IS_NULL		(MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 14)		),	//Result ByteArrayInputStream is null


    LOCAL_FILE_UPLOAD_SERVER_ERROR		        (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 100)		),	//文件服务器错误
    LOCAL_FILE_UPLOAD_COMMON_ERROR		        (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 102)		),	//文件上传失败
    LOCAL_FILE_UPLOAD_CANCEL_ERROR		        (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 103)		),	//文件上传失败

    LOCAL_FILE_DOWNLOAD_COMMON_ERROR		        (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 110)		),	//文件下载失败

    LOCAL_VOICE_READ_ERROR                       (MERR_MAKE(MopaNetConstants.LOCAL_ERROR, 111)		),	//录音文件读取失败


    NONE(99999999),
    OK(0),
    //  全局错误
    MERR_GLOBAL_NETWORK							(MERR_MAKE(MopaNetConstants.SERVER_GLOBAL, 1)		),	//  网络错误
    MERR_GLOBAL_TIMEOUT							(MERR_MAKE(MopaNetConstants.SERVER_GLOBAL, 2)		),	//  超时
    MERR_GLOBAL_PB_UNPACK						(MERR_MAKE(MopaNetConstants.SERVER_GLOBAL, 3)		),	//	protobuf解包失败
    MERR_GLOBAL_UNKNOWN_MSG						(MERR_MAKE(MopaNetConstants.SERVER_GLOBAL, 4)		),	//	未知消息
    MERR_GLOBAL_DBOP_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_GLOBAL, 5)		),	//	数据库操作出错

    /*user*/
    MERR_USER_PARAM_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_USER, 1)			),//	参数错误
    MERR_USER_DBOP_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_USER, 2)			),//	数据库操作错误
    MERR_USER_PROTO_PARSER_ERROR				(MERR_MAKE(MopaNetConstants.SERVER_USER, 3)			),//	协议解析出错
    MERR_USER_NETWORK_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_USER, 4)			),//	网络出错
    MERR_USER_NOT_EXIST							(MERR_MAKE(MopaNetConstants.SERVER_USER, 5)			),//	不存在的用户
    MERR_USER_AUTH_TIMEOUT						(MERR_MAKE(MopaNetConstants.SERVER_USER, 6)			),//	认证超时
    MERR_USER_PASSWORD_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_USER, 7)			),//	密码错误
    MERR_USER_TICKET_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_USER, 8)			),//	ticket错误
    MERR_USER_PERMISSION_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_USER, 9)			),//	权限出错
    MERR_USER_TICKET_TIMEOUT					(MERR_MAKE(MopaNetConstants.SERVER_USER, 10)			),//	ticket超时
    MERR_USER_UNSUPPORT_ALGO					(MERR_MAKE(MopaNetConstants.SERVER_USER, 11)			),//	不支持的认证算法

    /*im*/
    MERR_IM_PARAM_ERROR							(MERR_MAKE(MopaNetConstants.SERVER_IM, 1)			),	//	参数错误
    MERR_IM_DBOP_ERROR							(MERR_MAKE(MopaNetConstants.SERVER_IM, 2)			),	//	IM数据库出错
    MERR_IM_PROTO_PARSER_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_IM, 3)			),	//	协议解析出错
    MERR_IM_NETWORK_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_IM, 4)			),	//	网络出错

    MERR_GROUP_PARAM_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_IM, 101)			),//	参数错误
    MERR_GROUP_NOT_EXIST						(MERR_MAKE(MopaNetConstants.SERVER_IM, 102)			),//	群组不存在
    MERR_GROUP_DBOP_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_IM, 103)			),//	数据库操作出错
    MERR_GROUP_PROTO_PARSER_ERROR				(MERR_MAKE(MopaNetConstants.SERVER_IM, 104)			),//	协议解析出错
    MERR_GROUP_NETWORK_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_IM, 105)			),//	网络出错

    /*entry*/
    MERR_ENTRY_APP_NOT_FOUND					(MERR_MAKE(MopaNetConstants.SERVER_ENTRY, 1)			),//	找不到对应app

    /*dispatcher*/
    MERR_DISP_APP_NOT_SUPPORT					(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 1)	),	//	不支持的app类型
    MERR_DISP_MESSAGE_ORDER_ERROR				(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 2)	),	//	错误的消息顺序
    MERR_DISP_USER_NOT_FOUND					(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 3)	),	//	用户找不到
    MERR_DISP_APP_INVALID						(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 4)	),	//	无可用的应用
    MERR_DISP_APP_NETWORK_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 5)	),	//	应用网络出错
    MERR_DISP_PROTO_PARSER_ERROR				(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 6)	),	//	协议解析出错
    MERR_DISP_USER_LOGIN_CONFLICT				(MERR_MAKE(MopaNetConstants.SERVER_DISPATCHER, 7)	),	//	登录冲突

    /*mproxy*/
    MERR_MPROXY_PARAM_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_MPROXY, 1)		),	//	参数错误

    /*trends*/
    MERR_TRENDS_PARAM_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_TRENDS, 1)		),	//	参数错误
    MERR_TRENDS_DBOP_ERROR						(MERR_MAKE(MopaNetConstants.SERVER_TRENDS, 2)		),	//	数据库错误
    MERR_TRENDS_NOT_EXIST						(MERR_MAKE(MopaNetConstants.SERVER_TRENDS, 3)		),	//	动态不存在

    /*location*/
    MERR_LOCATION_PARAM_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_LOCATION, 1)),		//	参数错误
    MERR_LOCATION_DBOP_ERROR					(MERR_MAKE(MopaNetConstants.SERVER_LOCATION, 2));		//	数据库错误



    private int value;
    private static final HashMap< Integer, MopaErrno> map = new HashMap<Integer, MopaErrno>();
    static {
        for (MopaErrno e : MopaErrno.values())
        {
            map.put(e.value(), e);
        }
    }

    public static final int MERR_MAKE(int srv, int err)
    {
        return ((int)((int)0x80000000 | ((int)(((srv) << 16) | err))));
    }
    public static final int MERR_GET_SRV(int merr)
    {
        return (((merr) >> 16 ) & 0xffff);
    }
    public static final int  MERR_GET_ERR(int merr)
    {
        return ((merr) & 0xffff);
    }

    MopaErrno(int i){
        this.value=i;
    }
    public int value(){
        return this.value;
    }
    public static MopaErrno parse(int value) {
        return map.get(value);
    }

}
