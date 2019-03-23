
var chars = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];

function makeSecurityCode() {
     var securityCode = '';
     for(var i = 0; i < 6 ; i ++) {
         var id = Math.ceil(Math.random()*35);
         securityCode += chars[id];
     }
     return securityCode;
}
