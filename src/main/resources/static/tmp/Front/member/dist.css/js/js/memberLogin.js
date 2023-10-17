            const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
            const loginBtn_el = document.getElementById("loginBtn");
            loginBtn_el.addEventListener("click", function () {
                const memEmail = document.getElementById("memEmail").value;
                const memPassword = document.getElementById("memPassword").value;
                const member = {
                    "memEmail": memEmail,
                    "memPassword": memPassword
                }

                Login(member);
            })

            // const testloginBtn_el = document.getElementById("testloginBtn");
            // testloginBtn_el.addEventListener("click", async function () {
            //     const response = await fetch(baseUrl + 'member/testlogin', {
            //         method: "POST",
            //         headers: {
            //             "Content-Type": "application/json",
            //         }
            //     });
            //     console.log(response);
            // })
            // const logoutBtn_el = document.getElementById("logoutBtn");
            // logoutBtn_el.addEventListener("click", async function () {
            //     const response = await fetch(baseUrl + 'member/logout', {
            //         method: "POST",
            //         headers: {
            //             "Content-Type": "application/json",
            //         }
            //     });
            //     console.log(response);
            // })


            Login = async function Login(member) {
                // const member = {
                //     "memEmail": memEmail,
                //     "memPassword": memPassword
                // }
                const response = await fetch(baseUrl + "member/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(member)
                }); if (response.ok) {
                    Swal.fire({
                        icon: 'success',
                        title: '登入成功',
                        text: '',
                        confirmButtonText: '確定'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.history.go(-1);
                        }
                    })
        
                        }else if(response.status == 402){
                            Swal.fire({
                                icon: 'error',
                                title: '您已被停權',
                                text: '請聯繫客服信箱',
                                showCancelButton: true
                              })

                        } else {
                             Swal.fire({
                                            icon: 'error',
                                            title: '登入失敗',
                                            text: '請檢查您的帳號密碼',
                                            showCancelButton: true
                                          })
                         }

            }
