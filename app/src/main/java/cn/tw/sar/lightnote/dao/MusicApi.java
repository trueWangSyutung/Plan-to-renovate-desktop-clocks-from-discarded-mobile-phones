package cn.tw.sar.lightnote.dao;


import cn.tw.sar.lightnote.bean.EveryDayBean;
import cn.tw.sar.lightnote.bean.LoginBean;
import cn.tw.sar.lightnote.bean.LyricBean;
import cn.tw.sar.lightnote.bean.LyricDetailBean;
import cn.tw.sar.lightnote.bean.MusicBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MusicApi {
    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     * @return
     */

    @GET("/login/cellphone")
    Call<LoginBean> login(
            @Query("mobile") String phone,
            @Query("code") String code
    );

    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @GET("/captcha/sent")
    Call<LoginBean> sendCode(
            @Query("mobile") String phone
    );

    /**
     * 获取每日推荐歌曲
     * @return
     */
    @POST("/everyday/recommend")
    Call<EveryDayBean> getRecommend(

    );

    /**
     * hash=3A00ED44F18E45144056991CBAB5726A
     * @param hash
     * @return
     */
    @GET("/song/url")
    Call<MusicBean> getSongUrl(
            @Query("hash") String hash
    );

    /**
     * 获取歌词
     * @param hash
     * @return
     */
    @GET("/search/lyric")
    Call<LyricBean> getLyrics(
            @Query("hash") String hash
    );

    /**
     * 获取歌词
     * @return
     */
    @GET("/lyric")
    Call<LyricDetailBean> getLyric(
            @Query("id") String id,
            @Query("accesskey") String accesskey,
            @Query("decode") boolean decode,
            @Query("fmt") String fmt
    );
}
