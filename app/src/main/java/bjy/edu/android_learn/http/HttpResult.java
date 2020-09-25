package bjy.edu.android_learn.http;

public class HttpResult<T> {
    RspHeader RspHeader;
    T RspBody;

    public HttpResult.RspHeader getRspHeader() {
        return RspHeader;
    }

    public T getRspBody() {
        return RspBody;
    }

    class RspHeader{
        String ResponseMsg;
        String ResponseCode;
    }

    public class OpenBean{
        String OpenFlag;
        String RejectCount;

        public String getOpenFlag() {
            return OpenFlag;
        }

        public void setOpenFlag(String openFlag) {
            OpenFlag = openFlag;
        }

        public String getRejectCount() {
            return RejectCount;
        }

        public void setRejectCount(String rejectCount) {
            RejectCount = rejectCount;
        }
    }

    String reslut = "{\"RspHeader\":{\"ResponseMsg\":\"\",\"ResponseCode\":\"AAAAAAA\"},\"RspBody\":{\"OpenFlag\":\"1\",\"RejectCount\":\"0\"}}";
}
