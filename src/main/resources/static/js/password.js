const userPw = document.querySelector('#userPw');
const id = document.querySelector('#id');
const pwChecked = document.querySelector('#pwChecked');

const passwordCheckedFn = (event) => {
    event.preventDefault();

    const url = `/api/member/delete/passwordChecked/${userPw.value}/${id.value}`;

    fetch(url, {
     method: "GET"
     })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                alert("비밀번호 확인 중 오류가 발생했습니다.");
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        })
        .then(result => {
            if (result) {
                alert("비밀번호가 확인되었습니다.");
                pwChecked.value = "true";
            } else {
                alert("비밀번호가 일치하지 않습니다.");
                pwChecked.value = "false";
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("비밀번호 확인 요청 실패.");
        });
};


const deletePageOkFn = (event) => {
    event.preventDefault();

    if (pwChecked.value !== "true") {
        alert("비밀번호 확인 후 삭제를 진행할 수 있습니다.");

//        pwChecked.focus();
        return;
    }

    const url = `/api/member/delete/${id.value}`;

    fetch(url, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                alert("회원 삭제 중 오류가 발생했습니다.");
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        })
        .then(data => {
            if (data) {
                alert("회원이 성공적으로 삭제되었습니다.");
                window.location.href = "/member/memberList"; // 삭제 후 회원 목록으로 이동
            } else {
                alert("회원 삭제 실패.");
            }
        })
        .catch(err => {
            console.error("Error:", err);
            alert("회원 삭제 요청 실패.");
        });
};



