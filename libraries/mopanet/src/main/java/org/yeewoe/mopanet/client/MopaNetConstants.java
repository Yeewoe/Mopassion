package org.yeewoe.mopanet.client;

public class MopaNetConstants {

    public static final String HOSTNAME = "106.75.133.10";
    //public static final String HOSTNAME = "192.168.1.114";
    public static final int PORT = 10000;
    public static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds

    public static final short mark = (short)0xabcd;


    public static final int PK_HEADER_LEN = 4 + 2 + 2;

    //客户端的错误服务号
    public static final int LOCAL_ERROR = (0xFF); //255


    public static final int SERVER_GLOBAL			= (0x0) ;
    public static final int SERVER_USER			= (0x1) ;

    public static final int  SERVER_IM	  			=	(0x3);
    public static final int  SERVER_TRENDS			=	(0x4);
    public static final int  SERVER_LOCATION		=	(0x5);		//	LOCATION
    public static final int  SERVER_FILE			=	(0x6);		//	FILE
    public static final int  SERVER_MPROXY			=	(0x9);

    public static final int SERVER_DISPDATCHER_MAX	= (0xa) ;
    public static final int SERVER_RPC				= (0x7b);
    public static final int SERVER_MTUNNEL			= (0x7c);
    public static final int SERVER_DISPATCHER		= (0x7d);
    public static final int SERVER_ENTRY			= (0x7e);
    public static final int SERVER_MDBG			= (0x7f);





    public static int SRVOP_MAKE(int srv, int op)
    {
        return (((srv) << 16) | op);
    }
    public static int SRVOP_GET_SRV(int srvop)
    {
        return ((srvop) >> 16);
    }

    public static int SRVOP_GET_OP(int srvop)
    {
        return ((srvop) & 0xffff);
    }


    public static int SRVOP_REQ_TO_RSP(int srvop)
    {
        return ((srvop) + 1);
    }

    public static final int SRVOP_USR_CREATE_REQ                   = SRVOP_MAKE(SERVER_USER, 1);                   //PBUsrCreateReq
    public static final int SRVOP_USR_CREATE_RSP                   = SRVOP_MAKE(SERVER_USER, 2);                   //PBUsrCreateRsp
    public static final int SRVOP_USR_REMOVE_REQ                   = SRVOP_MAKE(SERVER_USER, 3);                   //PBUsrRemoveReq
    public static final int SRVOP_USR_REMOVE_RSP                   = SRVOP_MAKE(SERVER_USER, 4);                   //PBUsrRemoveRsp
    public static final int SRVOP_USR_SET_REQ                      = SRVOP_MAKE(SERVER_USER, 5);                   //PBUsrSetReq
    public static final int SRVOP_USR_SET_RSP                      = SRVOP_MAKE(SERVER_USER, 6);                   //PBUsrSetRsp
    public static final int SRVOP_USR_GET_REQ                      = SRVOP_MAKE(SERVER_USER, 7);                   //PBUsrGetReq
    public static final int SRVOP_USR_GET_RSP                      = SRVOP_MAKE(SERVER_USER, 8);                   //PBUsrGetRsp
    public static final int SRVOP_USR_BATCH_GET_REQ                = SRVOP_MAKE(SERVER_USER, 9);                   //PBUsrBatchGetReq
    public static final int SRVOP_USR_BATCH_GET_RSP                = SRVOP_MAKE(SERVER_USER, 10);                  //PBUsrBatchGetRsp
    public static final int SRVOP_USR_LOGIN_REQ                    = SRVOP_MAKE(SERVER_USER, 51);                  //PBUsrLoginReq
    public static final int SRVOP_USR_LOGIN_RSP                    = SRVOP_MAKE(SERVER_USER, 52);                      //PBUsrLoginRsp
    public static final int SRVOP_USR_AUTH_REQ                     = SRVOP_MAKE(SERVER_USER, 53);                  //PBUsrAuthReq
    public static final int SRVOP_USR_AUTH_RSP                     = SRVOP_MAKE(SERVER_USER, 54);                      //PBUsrAuthRsp
    public static final int SRVOP_USR_TICKET_REQ                   = SRVOP_MAKE(SERVER_USER, 55);                  //PBUsrTicketReq
    public static final int SRVOP_USR_TICKET_RSP                   = SRVOP_MAKE(SERVER_USER, 56);                  //PBUsrTicketRsp
    public static final int SRVOP_USR_LOGOUT_REQ            	= SRVOP_MAKE(SERVER_USER, 57);                 //PBUsrLogoutReq
    public static final int SRVOP_USR_LOGOUT_RSP            	= SRVOP_MAKE(SERVER_USER, 58);                 //PBUsrLogoutRsp
    public static final int SRVOP_USR_MODIFY_PASSWORD_REQ  	= SRVOP_MAKE(SERVER_USER, 59);                  //PBUsrModifyPasswdReq
    public static final int SRVOP_USR_MODIFY_PASSWORD_RSP   	= SRVOP_MAKE(SERVER_USER, 60);                 //PBUsrModifyPasswdRsp

    public static final int SRVOP_USR_FOLLOW_REQ				= SRVOP_MAKE(SERVER_USER, 61);			//PBUsrFollowReq
    public static final int SRVOP_USR_FOLLOW_RSP				= SRVOP_MAKE(SERVER_USER, 62);		//PBUsrFollowRsp

    public static final int SRVOP_USR_FOLLOW_LIST_REQ		=    SRVOP_MAKE(SERVER_USER, 63);			//PBUsrFollowListReq
    public static final int SRVOP_USR_FOLLOW_LIST_RSP		=    SRVOP_MAKE(SERVER_USER, 64);			//PBUsrFollowListRsp
    public static final int SRVOP_USR_FRIEND_LIST_REQ		=    SRVOP_MAKE(SERVER_USER, 65);			//PBUsrFriendListReq
    public static final int SRVOP_USR_FRIEND_LIST_RSP		=    SRVOP_MAKE(SERVER_USER, 66);			//PBUsrFriendListRsp

    public static final int SRVOP_USR_FANS_LIST_REQ		=    SRVOP_MAKE(SERVER_USER, 67);			//PBUsrFansListReq
    public static final int SRVOP_USR_FANS_LIST_RSP		=    SRVOP_MAKE(SERVER_USER, 68);			//PBUsrFansListRsp
    public static final int SRVOP_USR_FANS_COUNT_REQ 		=  	SRVOP_MAKE(SERVER_USER, 69);	//PBUsrFansCountReq
    public static final int SRVOP_USR_FANS_COUNT_RSP		= 	SRVOP_MAKE(SERVER_USER, 70); 	//PBUsrFansCountRsp
    public static final int SRVOP_USR_FOLLOW_COUNT_REQ	=	SRVOP_MAKE(SERVER_USER, 71);	//PBUsrFollowCountReq
    public static final int SRVOP_USR_FOLLOW_COUNT_RSP	= 	SRVOP_MAKE(SERVER_USER, 72); 	//PBUsrFollowCountRsp



    public static final int SRVOP_USR_KICKOUT_PUSH                 = SRVOP_MAKE(SERVER_USER, 201);         //PBUsrKickoutPush
    /* IM  */
    public static final int SRVOP_IM_SYNC_REQ                      = SRVOP_MAKE(SERVER_IM, 1);                     //PBImSyncReq
    public static final int SRVOP_IM_SYNC_RSP                      = SRVOP_MAKE(SERVER_IM, 2);                     //PBImSyncRsp
    public static final int SRVOP_IM_POST_MSG_REQ                  = SRVOP_MAKE(SERVER_IM, 3);                     //PBImPostReq
    public static final int SRVOP_IM_POST_MSG_RSP                  = SRVOP_MAKE(SERVER_IM, 4);                     //PBImPostRsp
    public static final int SRVOP_GROUP_CREATE_REQ                 = SRVOP_MAKE(SERVER_IM, 11);                    //PBGroupCreateReq
    public static final int SRVOP_GROUP_CREATE_RSP                 = SRVOP_MAKE(SERVER_IM, 12);                    //PBGroupCreateRsp
    public static final int SRVOP_GROUP_UPDATE_REQ                 = SRVOP_MAKE(SERVER_IM, 13);                    //PBGroupUpdateReq
    public static final int SRVOP_GROUP_UPDATE_RSP                 = SRVOP_MAKE(SERVER_IM, 14);                    //PBGroupUpdateRsp
    public static final int SRVOP_GROUP_DESTROY_REQ                = SRVOP_MAKE(SERVER_IM, 15);                    //PBGroupDestroyReq
    public static final int SRVOP_GROUP_DESTROY_RSP                = SRVOP_MAKE(SERVER_IM, 16);                    //PBGroupDestroyRsp
    public static final int SRVOP_GROUP_ADD_MEMBER_REQ             = SRVOP_MAKE(SERVER_IM, 17);                    //PBGroupAddMemberReq
    public static final int SRVOP_GROUP_ADD_MEMBER_RSP             = SRVOP_MAKE(SERVER_IM, 18);                    //PBGroupAddMemberRsp
    public static final int SRVOP_GROUP_DEL_MEMBER_REQ             = SRVOP_MAKE(SERVER_IM, 19);                    //PBGroupDelMemberReq
    public static final int SRVOP_GROUP_DEL_MEMBER_RSP             = SRVOP_MAKE(SERVER_IM, 20);                    //PBGroupDelMemberRsp
    public static final int SRVOP_GROUP_GET_REQ                     = SRVOP_MAKE(SERVER_IM, 21);                    //PBGroupGetReq
    public static final int SRVOP_GROUP_GET_RSP                     = SRVOP_MAKE(SERVER_IM, 22);


    /*	TRENDS	*/
    public static final int SRVOP_TRENDS_ADD_REQ			= SRVOP_MAKE(SERVER_TRENDS, 1)	;	//PBTrdAddReq
    public static final int SRVOP_TRENDS_ADD_RSP			= SRVOP_MAKE(SERVER_TRENDS, 2)	;	//PBTrdAddRsp
    public static final int SRVOP_TRENDS_REMOVE_REQ		= 	SRVOP_MAKE(SERVER_TRENDS, 3);		//PBTrdRemoveReq
    public static final int SRVOP_TRENDS_REMOVE_RSP		= 	SRVOP_MAKE(SERVER_TRENDS, 4);		//PBTrdRemoveRsp



    public static final int  SRVOP_TRENDS_GET_REQ			=SRVOP_MAKE(SERVER_TRENDS, 5);		//PBTrdGetReq
    public static final int SRVOP_TRENDS_GET_RSP			=SRVOP_MAKE(SERVER_TRENDS, 6);		//PBTrdGetRsp
    public static final int SRVOP_TRENDS_PULL_USER_REQ		=SRVOP_MAKE(SERVER_TRENDS, 7);		//PBTrdPullUserReq
    public static final int SRVOP_TRENDS_PULL_USER_RSP		=SRVOP_MAKE(SERVER_TRENDS, 8);



    public static final int SRVOP_TRENDS_FOLLOW_LIST_REQ	= SRVOP_MAKE(SERVER_TRENDS, 9)	;	//PBTrdFollowListReq
    public static final int SRVOP_TRENDS_FOLLOW_LIST_RSP	= SRVOP_MAKE(SERVER_TRENDS, 10)	;	//PBTrdFollowListRsp


    public static final int SRVOP_TRENDS_LIST_USER_LAST_REQ =	SRVOP_MAKE(SERVER_TRENDS, 11);		//PBTrdListUserLastReq
    public static final int SRVOP_TRENDS_LIST_USER_LAST_RSP =	SRVOP_MAKE(SERVER_TRENDS, 12);		//PBTrdListUserLastRsp
    public static final int SRVOP_TRENDS_NEAR_REQ			= 	SRVOP_MAKE(SERVER_TRENDS, 13);		//PBTrdNearReq
    public static final int SRVOP_TRENDS_NEAR_RSP			=	SRVOP_MAKE(SERVER_TRENDS, 14);		//PBTrdNearRsp
    public static final int SRVOP_TRENDS_LIKE_REQ			= 	SRVOP_MAKE(SERVER_TRENDS, 15);		//PBTrdLikeReq
    public static final int SRVOP_TRENDS_LIKE_RSP			=	SRVOP_MAKE(SERVER_TRENDS, 16);		//PBTrdLikeRsp

    /*	LOCATION	*/
    public static final int ASRVOP_LOCATION_REPORT_REQ		=	SRVOP_MAKE(SERVER_LOCATION, 1);		//PBLocReportReq
    public static final int ASRVOP_LOCATION_REPORT_RSP		=	SRVOP_MAKE(SERVER_LOCATION, 2);		//PBLocReportRsp
    public static final int ASRVOP_LOCATION_NEAR_USER_REQ	=	SRVOP_MAKE(SERVER_LOCATION, 3);		//PBLocNearUserReq
    public static final int ASRVOP_LOCATION_NEAR_USER_RSP	=	SRVOP_MAKE(SERVER_LOCATION, 4);		//PBLocNearUserRsp

    /*	FILE	*/
    public static final int SRVOP_FILE_UPLOAD_REQ			=   SRVOP_MAKE(SERVER_FILE, 1)		;	//PBFileUploadReq
    public static final int SRVOP_FILE_UPLOAD_RSP			=   SRVOP_MAKE(SERVER_FILE, 2)		;	//PBFileUploadRsp
    public static final int SRVOP_FILE_DOWNLOAD_REQ		=   	SRVOP_MAKE(SERVER_FILE, 3)	;		//PBFileDownloadReq
    public static final int SRVOP_FILE_DOWNLOAD_RSP		=   	SRVOP_MAKE(SERVER_FILE, 4)	;		//PBFileDownloadRsp

    /*	MPROXY   */
    public static final int SRVOP_MPROXY_REQ				= SRVOP_MAKE(SERVER_MPROXY, 1)	;	//PBMProxyReq
    public static final int SRVOP_MPROXY_RSP				= SRVOP_MAKE(SERVER_MPROXY, 2)	;	//PBMProxyRsp

    /* ENTRY鎿嶄綔 */
    public static final int SRVOP_ENTRY_APP_CONNECT_REQ            = SRVOP_MAKE(SERVER_ENTRY, 1);                  //PBEntryAppConnectReq
    public static final int SRVOP_ENTRY_APP_CONNECT_RSP            = SRVOP_MAKE(SERVER_ENTRY, 2);                  //PBEntryAppConnectRsp
    public static final int SRVOP_ENTRY_TIME_SWAP_REQ              = SRVOP_MAKE(SERVER_ENTRY, 51);         //PBEntryTimeSwapReq
    public static final int SRVOP_ENTRY_TIME_SWAP_RSP              = SRVOP_MAKE(SERVER_ENTRY, 52);         //PBEntryTimeSwapRsp
    public static final int SRVOP_ENTRY_NEW_PUSH                   = SRVOP_MAKE(SERVER_ENTRY, 201);                //PBEntryNewPush
    /*     DISPATCHERD鎿嶄綔       */
    public static final int SRVOP_DISP_APP_LOGIN_REQ               = SRVOP_MAKE(SERVER_DISPATCHER, 1);     //PBDispLoginReq
    public static final int SRVOP_DISP_APP_LOGIN_RSP               = SRVOP_MAKE(SERVER_DISPATCHER, 2);     //PBDispLoginRsp
    /* MTUNNEL鎿嶄綔*/
    public static final int SRVOP_MTUN_CREATE_SESSION_PUSH = SRVOP_MAKE(SERVER_MTUNNEL, 201);              //PBMTunSessionCreatePush
    public static final int SRVOP_MTUN_DESTROY_SESSION_PUSH        = SRVOP_MAKE(SERVER_MTUNNEL, 202);              //PBMTunSessionDestroyPush
    /* MDBG鎿嶄綔 */
    public static final int SRVOP_MDBG_RPC_REQ                     = SRVOP_MAKE(SERVER_MDBG, 1);                   //PBMDbgRpcReq
    public static final int SRVOP_MDBG_RPC_RSP                     = SRVOP_MAKE(SERVER_MDBG, 2);                   //PBMDbgRpcRsp



}
