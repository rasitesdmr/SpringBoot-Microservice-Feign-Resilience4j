# 🎯 Eureka Service Registry Nedir ?

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/eureka/s3.png" align="center">

* Bugünlerde bildiğimiz gibi, Microservice'ler etrafında çok fazla ivme var.


* Monolitik mimariden microservice tabanlı mimariye geçiş, sürdürülebilirlik (maintainability),
  ölçeklenebilirlik (scalability), yüksek kullanılabilirlik(high availability) vb. açısından gelecek için
  birçok fayda sağlar.


* Ancak aynı zamanda bu geçişi yaparken karşılaşılan birçok zorluk da var.


* Bunlardan biri de her bir microservice adresinin bakımını yapmaktır.


* Bu görev, servislerin sayısına ve dinamik yapılarına bağlı olarak son derece karmaşık olabilir.


* Tüm altyapı dağıtılmışsa ve bazı replikasyonlar da varsa, bu servis adreslerini korumak daha zor hale gelir.


* Bunu çözmek için, dağıtık bilgi işlemde (distributed computing)'Hizmet kaydı ve keşfi (Service registration and
  discovery)'adı verilen bir kavram vardır.


* Burada özel bir sunucu, dağıtılan ve kaldırılan tüm microservice'lerin kaydını tutmaktan sorumludur.


* Bu, diğer tüm uygulamaların/mikroservislerin telefon rehberi gibi hareket etmesini sağlar.


* Bunu, microservice'lerin (clients) kendilerini kaydedebilecekleri ve diğer kayıtlı microservice'leri
  keşfedebilecekleri bir arama hizmeti olarak düşünün.


* Bir istemci (client) microservice Eureka'ya kaydolduğunda, ana bilgisayar (host), bağlantı noktası (port) ve sağlık
  göstergesi (health indicator) gibi meta verileri sağlar ve böylece diğer mikro hizmetlerin onu keşfetmesine olanak
  tanır.


* Keşif sunucusu (discovery server) her microservice örneğinden düzenli bir kalp atışı (heartbeat) mesajı bekler.


* Bir örnek (instance) sürekli olarak kalp atışı (heartbeat) göndermemeye başlarsa, keşif sunucusu (discovery server)
  örneği kayıt defterinden (from his registry) kaldıracaktır.


* Bu şekilde, birbirleriyle işbirliği yapan çok istikrarlı bir microservice ekosistemine sahip olacağız ve bunun da
  ötesinde,diğer microservice'leri adreslerini manuel olarak tutmak zorunda kalmayacağız.

# 🎯 Eureka Server Nasıl Kullanılır ?

## 📌 Maven Dependencies

```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2021.0.5</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 📌 SpringBootApplication

```java

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

## 📌 application.properties

```properties
server.port=8761
spring.application.name=eureka-server
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

* eureka-server paketimize;


* İlk adım pom.xml dosyamıza bağımlılıklarımızı ekliyoruz.


* Application sınıfımıza @EnableEurekaServer anotasyonumuzu ekliyoruz.


* application.properties sınıfımıza özellikler ekliyoruz,bunlar;


* server.port : eureka server’ın çalışacağı port'u belirtiyoruz. Varsayılan portu 8761


* eureka.client.register-with-eureka : Değerini false olarak ayarladık. Nedeni, Eureka sunucusunun başlangıçta
  kendisini kaydetmesini önler.


* eureka.client.fetch-registry : Bir Eureka sunucusu başlatıldığında, varsayılan olarak diğer eş kayıt defterlerini
  arar. Bunu önlemek için false olarak ayarladık.


* Şimdi diğer servislerin eureka'ya kayıt olması için yapılması gereken adımlar;


* user-service ve department-service servislerime pom.xml dosyamıza aynı bağımlılıkları ekliyoruz.


* SpringBootApplication sınıflarına @EnableEurekaClient anotasyonumuzu ekliyoruz.


* Son olarak application.properties dosyasına şunları ekliyoruz;

## 📌 user-service

```properties
spring.application.name=user-service
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
```

## 📌 department-service

```properties
spring.application.name=department-service
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
```

* spring.application.name : Servisin adını belirleyeceğiz. Diğer servisler iletişim için bu ismi kullanabilirler.


* eureka.client.serviceUrl.defaultZone : user-service ve department-service servisleri eureka sunucusuna kaydolmak için
  bu URL'yi kullanacaktır.


* eureka.client.register-with-eureka : Bu, bu hizmetin kendisini Eureka sunucusuna kaydettirmesi gerektiğini gösterir

  ▶️eureka-server : http://localhost:8761/

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/eureka/s2.png">

# 🎯 OpenFeign Nedir ?

* İki web uygulaması veri alışverişi için birbirleriyle iletişim kurduklarında Üretici-Tüketici (Producer-Consumer)
  tekniği ile çalışırlar.


* Veri üreten bir uygulama Üretici/Sağlayıcı (Producer/Provider) uygulama olarak bilinir.


* Benzer şekilde veri tüketen uygulama da Tüketici (Consumer) uygulama olarak bilinir.


* Üretici uygulama için REST API'ye, Tüketici uygulama için ise RestTemplate'e aşina olabiliriz.


* Microservices tabanlı uygulamada da, iki Microservices birbiriyle iletişim kurar ve Üretici-Tüketici modelini takip
  eder.


* Burada, tüketici tarafında, kodlama çabamızı en aza indirmek için RestTemplate yerine daha iyi bir seçenek olarak
  'Feign Client' kavramını kullanıyoruz.


* REST servislerini kolay bir şekilde tüketmenin yanı sıra, FeignClient/OpenFeign Eureka ile birleştirildiğinde bize
  kolay bir yük dengeleme (load balancing) de sunar.


* İlk başta load balancer ne işe yarıyor onu anlatalım daha sonra openfeign nasıl kullanılır ona bakalım.

# 🎯 Load Balancer Nedir ?

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/load/load1.png" align="center"> 

* Yük dengeleme (load balancing), gelen ağ trafiğini sunucu çiftliği veya sunucu havuzu olarak da bilinen bir grup arka
  uç sunucusu arasında verimli bir şekilde dağıtmayı ifade eder.


* Modern yüksek trafikli web siteleri, kullanıcılardan veya istemcilerden gelen yüz binlerce, hatta milyonlarca
  eşzamanlı talebe hizmet vermeli ve doğru metin, resim, video veya uygulama verilerini hızlı ve güvenilir bir şekilde
  döndürmelidir.


* Bu yüksek hacimleri karşılamak üzere uygun maliyetli bir şekilde ölçeklendirmek için, modern bilgi işlemin en iyi
  uygulaması genellikle daha fazla sunucu eklemeyi gerektirir.


* Bir yük dengeleyici, sunucularınızın önünde oturan "trafik polisi" gibi davranır ve istemci isteklerini, hız ve
  kapasite kullanımını en üst düzeye çıkaracak şekilde bu istekleri yerine getirebilecek tüm sunucular arasında
  yönlendirir ve performansı düşürebilecek şekilde hiçbir sunucuya aşırı iş yüklenmemesini sağlar.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/load/load2.png" align="center"> 

* Tek bir sunucu devre dışı kalırsa, yük dengeleyici trafiği kalan çevrimiçi sunuculara yönlendirir.
* Sunucu grubuna yeni bir sunucu eklendiğinde, yük dengeleyici otomatik olarak istekleri bu sunucuya göndermeye başlar.
* Bu şekilde, bir yük dengeleyici aşağıdaki işlevleri yerine getirir:
* İstemci isteklerini veya ağ yükünü birden fazla sunucuya verimli bir şekilde dağıtır.
* İstekleri yalnızca çevrimiçi olan sunuculara göndererek yüksek kullanılabilirlik ve güvenilirlik sağlar.
* Talebin gerektirdiği şekilde sunucu ekleme veya çıkarma esnekliği sağlar.

# 🎯 Şimdi Gelelim OpenFeign'i Projemize Nasıl Ekleyeceğimize ?

## 📌 Maven Dependencies

```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## 📌 SpringBootApplication

```java

@SpringBootApplication
@EnableFeignClients("com.example.userservice.feignClients")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}

```

## 📌 FeignClients

```java

@FeignClient(name = "department-service")
public interface DepartmentFeignClient {

    @GetMapping(value = "/department/{id}")
    DepartmentResponse getById(@PathVariable("id") Long id);
}

```

* user-service servisinden department-service servisine istek attığımız için user-service hizmetimize bu özellikleri
  ekliyoruz.


* Feing bağımlılığımız olduğunda, uygulamamızın bunu kullanmasını sağlayabiliriz. Bunu yapmak için, ana uygulama
  sınıfımıza @EnableFeignClients ek açıklamasını eklememiz gerekir.


* Bu, sahte istemci olarak bildirilen arayüzlerin taranmasını sağlar.


* Bir arayüzü sahte istemci olarak bildirmek için @FeignClient ek açıklaması kullanılır.


* @FeignClient department-service ismindeki servise Get isteği atacaktır.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/feign/open2.png" align="center">

* user-service hizmetinden 1 numaralı id'ye sahip kullanıcının bilgilerini ve department bilgilerini çektim.


* Raşit isimli kullanıcının department id'si 1


* user-service hizmetinden istek attığım zaman FeignClient department-service ismini eureka server'den aradı ve
  @GetMapping("/{id}") isteğini tetikleyip bu bilgileri çekti.

# 🎯 Resilience4j Nedir ?

* Bilindiği üzere, arka-plan (back-end) servislerinin giderek karmaşıklaşması ve tek parça halinde sürdürülebilirliğinin
  zorlaşmasının sonucunda, mikroservis mimarisi kullanılarak arka-plan servislerinin birbirleriyle iletişim halinde
  olan, nispeten daha küçük servisler halinde düzenlenmesi oldukça popüler hale gelmiştir.


* Bu servisler, birbirleriyle kapalı bir ağ üzerinde, çoğunlukla HTTP protokolünü kullanarak haberleşmektedirler.


* Lakin, birbirleriyle HTTP üzerinden haberleşen servisler, bazı ek problemleri de beraberinde getirebilirler.


* Projemden örnek verirsem user-service servisi, kendisine gelen istekleri karşılamak üzere department-service
  servisiyle iletişime geçiyor olsun.


* department-service servisinde oluşabilecek bir sistem hatası, servisin yeni bir sürümünün sunucuya yüklenmesi veya
  yeni sürümde çıkabilecek istikrar sorunları gibi bir çok nedenden ötürü, department-service servisine giden isteklerin
  zamanlı bir biçimde yanıtlanamadığını ve bazı çağrılarda uygun bir cevap nesnesi yerine sunucu hatalarının
  döndürüldüğünü düşünün.


* Bu durumda, department-service servisinin döndürdüğü hata user-service servisine de sıçrayacaktır.


* Ardından, söz konusu hata department-service servisine çağrı yapılan katmandan itibaren üst katmanlara (servis,
  denetici (controller)vs.) fırlatılacak ve user-service servisine çağrı gerçekleştiren servisin de uygun bir yanıt
  alamamasına neden olacaktır.


* Bu şekilde oluşan bir hata yayılım zinciri, son kullanıcının söz konusu web uygulamasını arzu ettiği bir biçimde
  kullanamamasıyla sonuçlanacaktır.


* Bu durumlar ne gibi yöntemlerle giderilebilir.

## 📌 Retry

* Beklenmedik bir yanıtın - ya da yanıt alınamamasının - isteği tekrar göndererek düzeltilebileceğini varsaydığımızda,
  yeniden deneme kalıbını kullanmak yardımcı olabilir. Bu, işlem başarısız olarak işaretlenmeden önce başarısız
  isteklerin yapılandırılabilir sayıda yeniden denendiği çok basit bir modeldir.


* Aşağıdaki durumlarda yeniden denemeler yararlı olabilir:


* Paket kaybı gibi geçici ağ sorunları.

* Hedef hizmetin dahili hataları, örneğin bir veritabanı kesintisinden kaynaklanan.


* Hedef hizmete yönelik çok sayıda talep nedeniyle yanıt alınamaması veya yavaş yanıt alınması.

## 📌 Fallback

* Geri dönüş kalıbı, hizmetinizin başka bir hizmete yapılan başarısız bir istek durumunda yürütmeye devam etmesini
  sağlar. Eksik bir yanıt nedeniyle hesaplamayı iptal etmek yerine, bir geri dönüş değeri doldururuz.

## 📌 Timeout

* Zaman aşımı modeli oldukça basittir ve birçok HTTP istemcisinin yapılandırılmış varsayılan bir zaman aşımı vardır.
  Amaç, yanıtlar için sınırsız bekleme sürelerinden kaçınmak ve böylece zaman aşımı içinde yanıt alınamayan her isteği
  başarısız olarak değerlendirmektir.

## 📌 Circuit breaker

* Circuit Breakers deseni, adından anlaşılacağı üzere elektronik devrelerdeki, devre kesici şalt cihazlar gibi
  kurgulanan bir yöntemdir.


* Devre kesiciler, elektronik devreyi korumak için sistemde meydana gelen bir aksaklık durumunda (yük akımını veya kısa
  devre akımları) yük geçişini durdururlar.


* Circuit Breakers deseni uygulandığında, servisler arasında haberleşmeyi kapsayacak şekilde inşaa edilir.


* Servisler arasındaki iletişimi (Event, Message, Http, vb.) izler ve haberleşmedeki meydana gelen hataları takip eder.


* Request yapılan bir API ucunun, http 500 hata kodu dönmesi veya fırlatılan bir event’in handle edilememesi bu hata
  duruma örnek olarak gösterilebilir.


* Sistemde meydana gelen hata durumu belirli bir eşik değerini geçtiğinde ise Circuit Breakers açık duruma geçer ve
  haberleşmeyi keser, daha önce belirlenen hata mesajlarını döndürür.


* Bir süre bekledikten sonra devre yarı açık duruma geçer. Bu durumda bir isteğin geçmesine izin verir ve başarısız
  olması durumunda açık duruma veya başarılı olması durumunda kapalı duruma geri döner.


* Circuit Breakers açık durumdayken haberleşme trafiğini izlemeye devam eder ve istek yapılan servis veya fırlatılan bir
  event başarılı sonuçlar dönmeye başlamışsa kapalı duruma geçer.


* Circuit Breakers’ın üç durumu vardır. Bu durumlar: Açık (Open), Kapalı (Closed) ve Yarı-Açık (Half-Open).

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res1.png" align="center">

### Closed

* Sigorta tamamen kapalıdır. Bütün çağrıların yapılmasına izin verilir ve hatalı çağrılar kurtarma metoduna
  yönlendirilebilir (fallback). Hatalı çağrıların sayısının (veya oranının) belirli bir sayının üstünde olması
  takdirinde, sigorta, açık konuma getirilir.

### Open

* Sigorta aktif konumdadır ve çağrıların tamamını reddetmektedir. Reddedilen çağrılar, mikroservis içerisinde yer alan
  bir kurtarma metoduna yönlendirilerek çağrının sorunsuz bir biçimde sonuçlanması sağlanabilir.

### Half-Open

* Sigortanın açık konuma geçmesinden belirli bir süre sonra, sigorta, kendini yarı açık konuma getirir. Bu durumda
  belirli sayıda (veya oranda) çağrının gerçekleştirilmesine izin verilir. Eğer hatalı çağrıların oranı (veya sayısı)
  belirli bir sayının üzerinde olursa, tekrardan açık konuma geçilir; aksi takdirde sigorta tamamen kapatılır.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res2.png">

# 🎯 Şimdi Gelelim Resilience4j Projemize Nasıl Ekleyeceğimize ?

## 📌 Maven Dependencies

```xml

<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
</dependency>
```

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## 📌 CircuitBreaker

```java

@FeignClient(name = "department-service")
public interface DepartmentFeignClient {

    @GetMapping(value = "/department/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getDepartmentByIdFallback")
    DepartmentResponse getById(@PathVariable("id") Long id);

    default DepartmentResponse getDepartmentByIdFallback(Throwable t) {
        return new DepartmentResponse();
    }
}
```

## 📌 application.properties

```properties
resilience4j.circuitbreaker.instances.userService.sliding-window-type=count_based
resilience4j.circuitbreaker.instances.userService.sliding-window-size=10
resilience4j.circuitbreaker.instances.userService.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.userService.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.instances.userService.waitDurationInOpenState=5s
resilience4j.circuitbreaker.instances.userService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.userService.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.userService.eventConsumerBufferSize=10
resilience4j.circuitbreaker.instances.userService.automaticTransitionFromOpenToHalfOpenEnabled=true
management.health.circuitbreakers.enabled=true
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always
feign.circuitbreaker.enabled=true
```

* user-service servisimiz department-service servisinden yanıt alamasa neler gerçekleşecek onlara bakalım.
* İlk başta yurakıdaki özellikleri ekleyelim.


* @CircuitBreaker(name = "userService", fallbackMethod = "getDepartmentByIdFallback") : name properties dosyasına
  eklediğimiz adla aynı olmalı. fallbackMethod metodu ise department-service hizmetinden yanıt alamazsa
  getDepartmentByIdFallback metodunu tetikleyecektir. Buda bize boş department nesnesi döndürecektir.


* sliding-window-type : Time-Based devre kesiciler, bir hizmet geçici olarak kullanılamadığında ancak belirli bir
  süre içinde düzeleceğinde kullanışlıdır. Count-Based devre kesiciler, bir hizmet sürekli olarak arızalandığında ve
  daha kapsamlı sorun giderme gerektirdiğinde kullanışlıdır.


* sliding-window-size: 10 olarak ayarlarsak, bu da circuit breaker kullanıcı hizmetine yapılan son 10 çağrıyı izleyeceği
  anlamına gelir. Bu çağrıların belirli bir yüzdesinden fazlası başarısız olursa, circuit breaker devreye girecek ve
  hizmete istek göndermeyi durduracaktır. silding windows size sona erdiğinde başarısızlık oranı yeniden hesaplanır ve
  eşiğin altındaysa circuit breaker isteklere tekrar izin verir.


* minimumNumberOfCalls: Minimum çağrı sayısı, circuit breaker'ın açma yapıp yapmama konusunda bir karar verebilmesi için
  kaydedilmesi gereken minimum çağrı sayısıdır. Bu da kullanıcı servisine en az 5 çağrı yapılmadıkça circuit breaker
  tetiklenmiyeceği anlamına gelir. Bu sistem başlatılırken veya yoğun olarak kullanılmadığında circuit breaker'in çok
  erken tetiklenmesini önlemek için kullanışlıdır.


* permittedNumberOfCallsInHalfOpenState : Bir circuit breaker'ın 3 durumu vardır. Closed, open, half-open. Bir circuit
  breaker kapalı olduğunda,isteklerin servise geçmesine izin verir. Açık olduğu durumda gelen istekleri engeller.
  Yarı-açık olduğunda, doğru çalışıp çalışmadığını görmek için sınırlı sayıda isteğin servise geçmesine izin verir. Bu
  durumda en fazla 3 isteğin service geçmesine izin vereceği anlamına gelir.


* waitDurationInOpenState : Open durumda bekleme süresi, circuit breaker'ın half-open durumuna geçmeden önce ne kadar
  süre open durumda kalması gerektiğini belirleyen bir circuit breaker özelliğidir. Bu durumda circuit breaker açılıp
  açık duruma geçtiğinde, yarı-açık duruma geçmeden önce 5 saniye boyunca bu durumda kalacağı anlamına gelir. Bu süre,
  hizmete kendini toparlaması için zaman tanır ve sistemin hizmet hala mecvut değilken isteklere boğulmasını önlemesini
  sağlar.


* failureRateThreshold: Bu kullanıcı hizmetine gelen isteklerin %50 sinden fazlasının başarısız olması durumunda circuit
  breaker'ın devreye gireceği ve iyileşme şansı elde edene kadar hizmete başka isteklerin gönderilmesi engelliyeceği
  anlamına gelir.


* registerHealthIndicator: Circuit Breaker'ın durumunu kontrol etmemizi sağlayan bir izleme aracıdır. Circuit Breaker'ın
  açık, kapalı veya yarı-açık olup olmadığını belirlemek ve başarılı ve başarısız çağrıların sayısı hakkında bilgi
  sağlamak için kullanılabilir.

# 🎯 Şimdi Gelelim Bunlar Nasıl Çalışıyor ?

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res4.png">

* İlk başta user-service ve department-service hizmetlerim ayakta ve sıkıntısız çalışıyor.
* Yukarıda görüldüğü gibi CircuitBreaker closed durumunda.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res5.png">

* Ben department-service hizmetimi çökertsem user-service hizmetim department bilgilerini alamıyacak.
* properties dosyasında CircuitBreaker tetiklenmesi için 5 istek alması gerekiyor.
* 5 tane istek attığımda circuitBreaker OPEN durumuna geçiyor.
* 5 saniye istek atmadan beklersem circuitBreaker HALF-OPEN durumuna geçecek.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res6.png">

* Burda HALF-OPEN durumu ya OPEN durumuna ya da CLOSED durumuna geçmesi gerekiyor.
* Bunuda properties dosyasında 3 tane istek atıp eğer 3 isteğin 2 si başarısızsa OPEN durumuna başarılıysa CLOSED
  durumuna geçmesini söylüyoruz.
* Başarısız istek atarsam.

<img src="https://github.com/rasitesdmr/SpringBoot-Microservice-Feign-Resilience4j/blob/master/images/resi/res7.png">

# 🎯 Link 

▶️eureka-server : http://localhost:8761/

▶️circuitbreaker : http://localhost:8082/actuator/health

▶️user-service swagger : http://localhost:8082/swagger-ui.html

▶️department-service swagger : http://localhost:8081/swagger-ui.html

## 📌 docker-compose.yaml

```yaml
docker-compose up --build -d
```

```yaml
docker-compose down -v
```




