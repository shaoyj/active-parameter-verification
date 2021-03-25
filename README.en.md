# active-parameter-verification

#### Description
基于java的 主动式参数校验工具

#### Software Architecture
Software architecture description

#### Installation

1.  git clone https://gitee.com/Mylomen/active-parameter-verification.git
2.  cd  active-parameter-verification
3.  mvn clean && mvn deploy
4.  引入maven依赖

#### Instructions

```aidl
/** FlyReq **/
@Setter
@Getter
public class FlyReq implements Serializable {
 
 
    private static final long serialVersionUID = 5300811450389900248L;
 
 
    @XxqParams(noEmpty = "name 不能为空")
    private String name;
 
    @XxqParams(noNullAndZero = "age 不能为null 和 0")
    private Integer age;
 
 
}
 
 
/** FlyRemoteService **/
public interface FlyRemoteService {
 
    /**
     * 搞事情 1.0
     *
     * @param map
     */
    void go(@XxqParams.MapList({
            @XxqParams(key = "name", noEmpty = "name 非法"),
            @XxqParams(key = "age", noNullAndZero = "age 非法"),}) Map<String, Object> map);
 
 
    /**
     * 搞事情 2.0
     *
     * @param flyReq
     */
    void go(FlyReq flyReq);
 
 
    /**
     * 搞事情 3.0
     *
     * @param name
     * @param age
     */
    default void go(@XxqParams(noEmpty = "name 不能为空") String name,
                    @XxqParams(noNullAndZero = "age 不能为null 和 0") Integer age) {
        //TODO 暂时不支持
    }
}
 
 
/** BirdController **/
public class BirdController implements FlyRemoteService {
 
 
    @Override
    public void go(Map<String, Object> map) {
 
        String errMsg = XxqParamsUtils.activeVerify(map);
 
        System.out.println("errMsg is " + errMsg);
    }
 
    @Override
    public void go(FlyReq flyReq) {
 
        String errMsg = XxqParamsUtils.activeVerify(flyReq);
 
        System.out.println("errMsg is " + errMsg);
    }
 
}
 
 
/** 测试main **/
 
public static void main(String[] args) {
    System.out.println("######### flyReq 没有赋值 #########");
    FlyReq flyReq = new FlyReq();
    FlyRemoteService flyRemoteService = new BirdController();
    flyRemoteService.go(flyReq);
 
    System.out.println("\n######### flyReq 赋值 age #########");
    flyReq.setAge(1);
    flyRemoteService.go(flyReq);
 
    System.out.println("\n######### flyReq 赋值 name,age #########");
    flyReq.setName("syj");
    flyRemoteService.go(flyReq);
 
 
    System.out.println("\n######### map 没有赋值 #########");
    BirdController birdController=new BirdController();
    Map<String, Object> map=new HashMap<>();
    birdController.go(map);
 
 
    System.out.println("\n######### map 赋值 name #########");
    map.put("name","syj");
    birdController.go(map);
 
 
    System.out.println("\n######### map 赋值 name,age #########");
    map.put("name","syj");
    map.put("age",1);
    birdController.go(map);
 
}

```

#### Contribution

1.  Fork the repository
2.  Create Feat_xxx branch
3.  Commit your code
4.  Create Pull Request


#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
