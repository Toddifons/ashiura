# ashiura
YHdatabases-Project userWep,API



각 클래스에 대한 설명

com.shiromi.ashiura.config.Async.SpringAsyncConfig
@Async 어노테이션을 잘 쓰기 위한 쓰레드풀을 조정하는 클래스 , 비동기메소드를 사용하지 않아 사실상 사용되지 않음

com.shiromi.ashiura.config.jwt.JwtAuthenticationFilter
JWT를 쓰기위해 GenericFilterBean을 상속받아 오버라이드 한 클래스
리퀘스트 헤더에서 JWT 토큰을 가져오고, 유효성검사를 한뒤 SecurityContext에 Authentication 객체를 저장함 
doFilter()
내부메소드인 resolveToken()로 리퀘스트 헤더안의 JWT 액세스토큰만 선별해서 가져오고
유효성 검사에 if문으로 jwtProvider.validateToken() 메소드를 사용한뒤 인증된 유저의 정보를 얻는데에 
jwtProvider.getAuthentication()를 쓰고 SecurityContext에 Authentication 객체를 저장함
resolveToken()
헤더들 안에서"Cookie"라는 헤더를 가져온뒤 .startsWith나 .indexOf로 JWT외의 부분을 잘라낸뒤 해당 문자열을 반환하는 메소드,
리프레쉬 토큰은 발급은 되었으나 사용되지 않음

com.shiromi.ashiura.config.jwt.JwtProvider
JWT 시크릿 키를 만들고 인증된 유저 정보를 가지고 액세스,리프레쉬 토큰을 발급하는 클래스
generateToken()
인증이 통과 된 유저 정보를 파라미터로 토큰을 생성 발급될 토큰의 각종 정보를 빌드체인 형식으로 수정가능
getAuthentication()
토큰을 parseClaims()메소드로 복호화 하고 토큰정보를 얻고 클레임에서 권한정보를 가져온뒤 유저디테일 객체에 Authentication을 담아 리턴 하는 메소드
validateToken()
토큰정보를 검증하는 메서드
유효하지 않은 토큰, 만료된 토큰, 지원되지 않은 토큰, 비어있는 토큰에 대해 대응하는 익셉션 처리
parseClaims()
토큰 복호화에 쓰이는 메소드, 시크릿키를 가져다 씀

com.shiromi.ashiura.config.PasswordEncodeConfig
passwordEncoder()를 호출 할경우 BCryptPasswordEncoder()를 반환하는 메서드, 순환참조 방지

com.shiromi.ashiura.config.WebSecurityConfigure
스프링시큐리티 빌드체인 구성을 하는 클래스
.addFilterBefore에 JWT 클래스 들이 쓰임



-- construction zone --
