# demo

## 기능 구현, 유지보수 중 주의할 점

* `@/page` 하위의 컴포넌트에 `@Options` 에 들어갈 설정이 없더라도 `({})` 를 반드시 입력해야 한다
  * 동적으로 라우팅 생성하기 위해, `require.context` 로 컴포넌트 정보를 불러올 때 `({})` 유무에 따라서 결과가 다르고, 없으면 라우팅 생성에 실패한다
* `<script></script>` 에 `lang="ts"` 를 넣어줘야 typescript 로 작성할 수 있다
  * `<script lang="ts"></script>`
* 외부 모듈을 추가하고, 해당 기능을 storybook 에서 보여줘야 할 경우에도 설정을 추가해줘야 storybook 에서 외부 모듈을 인식한다
  * 외부 css 등을 사용할 때, `src/main.ts` 에 import 뿐 아니라 `.storybook/preview.js` 에도 추가를 해줘야 storybook 에서도 적용된다
  * addon 으로 구현된 외부 모듈이 있는 경우, addon 추가 및 설정으로 해결할 수 있다

## 폴더 구조

### 라우팅

* [vue router](https://next.router.vuejs.org/) 를 기반으로 동작한다
* page 폴더 하위의 `index.ts` 로 page 하위에 있는 모든 `*.vue` 파일을 디렉토리 구조로 라우팅을 처리한다
* 폴더마다 url 에서 `/폴더이름` 으로 구분된다
* 한 폴더 안에 `index.vue` 를 제외한 다른 이름의 파일이 존재할 경우, 해당 이름으로 `$route.params` 에서 접근이 가능하다
  * ex) `_id.vue` 파일은 해당 파일의 스크립트 안에서 `this.$route.params.id` 으로 url 에 id 부분에 해당되는 값을 접근할 수 있다
    * `/path/to/_id.vue` 파일을 `/path/to/2` 이렇게 접근하면 `this.$route.params.id` 는 2 로 반환된다
  * `index.vue` 는 해당 경로로 접근하면 보여지는 파일이다

### 레이아웃

* `App.vue` 파일에서 현재 라우팅에 사용되는 파일의 layout 값으로 페이지 전체 레이아웃을 지정한다
  * `@/page` 하위의 layout 설정만 동작하고, `@/components` 등의 파일에 있는 레이아웃 설정은 동작하지 않는다
* `@/layouts` 하위에 레이아웃을 등록하면 모든 `@/page` 하위의 컴포넌트에서 사용가능하다
* 기본 레이아웃은 `default.vue` 이다
  * 등록되지 않은 레이아웃을 사용하거나, 설정 오류 등으로 인해 레이아웃을 찾을 수 없을 때 `default.vue` 가 적용된다
* 사용 예시

```typescript
@Options({
    ...
    layout: 'layoutName',
    // .vue 를 제외한 파일 이름을 작성하면, 해당 레이아웃이 적용된다
    // @/page 하위의 폴더에 있는 컴포넌트만 layout 설정이 동작한다
    ...
})
```

### axios

* @/plugin/axios 에 설정을 추가하여 전역 플러그인으로 설정하여 `this.$axios` 로 접근이 가능하다
* vue.config.js 파일에 proxy 설정이 존재한다
  * 프록시 설정이 적용되기 위해서는 path 에 /api 가 붙어있어야 한다

```typescript
const result = await this.$axios.get('/api/some-path'); // proxy 설정이 동작한다
const result2 = await this.$axios.get('/some-path');    // proxy 설정이 동작하지 않는다
// 별도의 url 없이 path 만 입력되면, vue.config.js 설정인 localhost:3000/some-path 로 요청이 된다
const result3 = await this.$axios.get('www.abc.com/some-path');
// 위와같이 전체 url 을 입력해도, 해당 url 로 요청이 되지 않고 localhost:3000/www.abc.com/some-path 로 요청이 된다

```

### dotenv

* 프로젝트 root 하위에 .env[.NODE_ENV] 형태로 기본 .env 을 overwrite 가 가능하다
  * 실행 스크립트에 `--mode NODE_ENV` 형태로 추가하면, .env[.NODE_ENV] 가 .env 를 overwrite 하여 process.env 에 설정된다
  * 빌드시 최종 process.env
    * .env 에만 존재 할 경우, 해당 값은 process.env 에 존재한다
    * .env 와 .env.NODE_ENV 가 같이 존재 할 경우 .env.NODE_ENV 의 값이 process.env 에 존재한다
* `VUE_APP` 이라는 prefix 를 반드시 붙여야 process.env 에서 인식이 된다
  * `VUE_APP_VALUE='value'` 는 process.env 에 존재하지만, `VALUE='value'` 는 process.env 에 존재하지 않는다

```
// .env
VUE_APP_VALUE1='abc'
VUE_APP_VALUE2=123
// .env.prod
VUE_APP_VALUE1='def'

yarn serve --mode prod
// process.env 에는 설정된 값
// 기본 process.env + (VUE_APP_VALUE1='def', VUE_APP_VALUE2=123)
```

### vue-i18n

* 국제화 설정은 `this.$i18n` 객체에 접근하여 변경할 수 있다
* 국제화 변환은 `this.$t('key', ...)`/`this.$tc('key', ...)` 등으로 현재 설정된 언어로 변환이 가능하다

```typescript
// 국제화 설정 변경
this.$i18n.locale = 'en';

// 스크립트에서 국제화 사용
this.$t('TEST');
```

```vue
<!--vue 의 html 에서 사용-->
<sapn>{{$t('TEST'}}</sapn>
```

### filters

* vue 3.x 부터 filter 는 삭제되어 더이상 지원하지 않는다
    * method/computed 속성으로 대체하도록 권장하고 있다
* globalProperties 에 속성을 추가하여, 이전에 사용하듯 사용할 수 있으며 `plugins/filters` 에 정의되어 있다
    * 추가된 속성은 `this.$filters.customFilter(value)` 형태로 사용할 수 있다
    * 컴포넌트/class 레벨에서 getter 를 사용하기를 권장하지만, 한곳에서 정의하여 사용할 수 없는 경우에 filters 에 추가하여 사용한다

```typescript
// plugins/filters.ts
const filters = {
    ...
    customFilter(value) {
        ...
        return res;
    }
}

// in script
this.$filters.customFilter(val);
```

### global component

* `/plugins/global-components` 폴더의 index.ts 에 전역으로 사용할 컴포넌트를 등록하면 import 없이 모든 곳에서 해당 컴포넌트를 사용할 수 있다
* `app.component('some-component', component)` 로 전역으로 사용할 컴포넌트만 등록하면 `<some-component />` 형태로 import 없이 사용할 수 있다

```typescript
import someComponent from 'some/component/path/someComponent';

export default (app: App): App => {
    app.component('html 에 사용할 태그 이름', someComponent);
    // 전역으로 등록할 컴포넌트를 import 한 후, 해당 컴포넌트를 등록해주면 전역으로 사용할 수 있다

    return app;
};
```

## [storybook](https://storybook.js.org/)

### [atomic design](https://bradfrost.com/blog/post/atomic-web-design/)

* 해당 방식을 바탕으로 컴포넌트를 구성한다
* 폴더 구조는 아래와 같이 구성하고, 재사용이 가능한 공통 컴포넌트들은 `components` 하위의 atom/molecules/organisms/templates 아래에 컴포넌트별로 관리한다
* 하나의 컴포넌트의 기능이 커질경우, 해당 컴포넌트를 분리하여 관리한다
  * `/src/components/atom/button/index.vue`, `/src/components/atom/button/addition.vue`
  * 처음 컴포넌트를 만들때는 `index.vue` 로 생성하여 기본 컴포넌트를 생성한 후, 추후 기능 추가 등으로 사이즈가 커질 경우 해당 컴포넌트 폴더에서 파일을 분리하여 기능별로 관리한다

```
components-┬- atom
           |- molecules
           |- organisms
           |- templates
pages------┬- page1
           |- page2
```

## 사용한 플러그인

### [vue-router](https://next.router.vuejs.org/)
### [vue-class-component](https://github.com/vuejs/vue-class-component), [vue-property-decorator](https://github.com/kaorun343/vue-property-decorator)
### [axios](https://github.com/axios/axios)
### [dotenv](https://github.com/motdotla/dotenv)
### [vue-i18n](https://github.com/kazupon/vue-i18n)
### [primevue](https://www.primefaces.org/primevue/), [primeflex](https://www.primefaces.org/primeflex/)

## 프로젝트 실행

### npm version

* npm 6 이하 버전
  * [peerDependencies 충돌 시 npm 7 이후부터는 설치를 차단. 6 이전은 경고.](https://github.blog/2021-02-02-npm-7-is-now-generally-available/)
  * 관련 모듈 버전 업데이트에 따라 지원 버전 변경 예정.
  * 관련 모듈 (2021.11.04 기준) : vuex-class ( peerDependencies 중 "vue": "^2.5.0" )
  
```
# npm version change
npm install -g npm@6
```

### 로컬 실행

```
npm install
yarn serve
```

### 로컬 테스트 실행

```
yarn test:unit
```

### 로컬 빌드

```
yarn build
```
