requirejs.config({
    baseUrl: '/apiTest/resources/js',
    waitSeconds: 200,
    paths: {
       'jquery': 'jquery/jquery-3.3.1.min',
       'daum' : '//dapi.kakao.com/v2/maps/sdk.js?appkey=b41c7a1412b3f5a7b5483f019fc099d8&autoload=false',
       'xDomain': 'jquery.ajax-cross-origin.min',
       'daumLibrary': '//dapi.kakao.com/v2/maps/sdk.js?appkey=b41c7a1412b3f5a7b5483f019fc099d8&libraries=services,clusterer,drawing'
    },
    shim: {
    	'jquery': {
            exports: 'jquery'
        },
        'daum': {
        	exports: 'daum'
        },
        'xDomain': {
        	exports: 'xDomain'
        },
        'daumLibrary' : {
        	exports: 'daumLibrary'
        }
        
    }
});
