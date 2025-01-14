//접속
const connectBtn=document.querySelector('#connectBtn');
//접속자 아이디
const userId=document.querySelector('#userId');
//메시지 전송
const sendBtn=document.querySelector('#sendBtn');
//메시지
const msg=document.querySelector('#msg');
//채팅창
const chatWindowCon=document.querySelector('.chatWindow-con');
//전송데이터
let data={}
//웹소켓 URL
let webSocket;
const url='localhost:8094'



connectBtn.addEventListener('click',()=>{
//1.웹소켓 연결
 webSocket=new WebSocket(`ws://${url}/chat`)

 if(userId.value.length<=0 || userId.value==""){
    alert("접속자를 입력해주세요")
    userId.focus();
    return;
 }

 alert(`${userId.value}님 접속하였습니다`);

 //2. onMessage 받기 (접속시에)
   webSocket.onmessage=(msg)=>{
   //문자열 -> JSON변환
   const socketData=JSON.parse(msg.data);
   console.log(socketData+" <<< socketData")
   console.log(socketData.userId+" <<< userId")
   console.log(socketData.msg+" <<< msg")
   console.log(socketData.date+" <<< date")

   //div
   const divTag=document.createElement('div');
   //내글과 남의글 구분
   let className;
   //전송아이디와 채팅 대상이 같으면 -> 본인글  class이름 chat-main
   //전송아이디와 채팅 대상이 다르면 -> 남의글  class이름 chat-sub

   if(socketData.userId==userId.value){
    className='chat-main';
   }else{
    className='chat-sub';
   }

    let item=`
        <div class='${className}'>
            <p>
                <span>[작성자]: ${socketData.userId}</span>
                <span>[작성시간]: ${socketData.date}</span>
                <span>[내용]: ${socketData.msg}</span>
            </p>
        </div>
    `;

    divTag.innerHTML=item;

    console.log(divTag+" <<<divTag");
    chatWindowCon.append(divTag);

   }

});




const sendMessageFn=()=>{
    if(msg.value.trim().length>0){
        data.userId=userId.value;
        data.msg=msg.value;
        data.date=new Date().toLocaleString();

       let sendData= JSON.stringify(data);

       //메시지 전송
       webSocket.send(sendData);
    }

}

sendBtn.addEventListener('click',sendMessageFn);


