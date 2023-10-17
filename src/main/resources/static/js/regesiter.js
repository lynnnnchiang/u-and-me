(() => {
	const regBottom = document.querySelector('#regBottom');
	const userName = document.querySelector('#userName');
	const inputPassword = document.querySelector('#inputPassword');
	const inputPhone = document.querySelector('#inputPhone');
	const inputAddress = document.querySelector('#inputAddress');
	const inputs = document.querySelectorAll('input');
	const inputState = document.querySelector('#inputState');
	regBottom.addEventListener('click', () => {
		const accLength = username.value.length;
		const accValue = userName.value;
        		if (!isValidEmail(accValue)) {
        			//補顯示錯誤訊息
        		}
		// 正則表達式檢查信箱格式
         	function isValidEmail(email) {
         		const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
         		return emailRegex.test(email);
         	}
         })();

		msg.textContent = '';
		const fileReader = new FileReader();
		fileReader.addEventListener('load', e => {
			const imageBase64 = btoa(e.target.result);
			fetch('register', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({
					username: username.value,
					password: password.value,
					nickname: nickname.value,
					image: imageBase64
				}),
			})
			.then(resp => resp.json())
			.then(body => {
				const { successful } = body;
				if (successful) {
					for (let input of inputs) {
						input.disabled = true;
					}
					btn1.disabled = true;
					msg.className = 'info';
					msg.textContent = '註冊成功';
				} else {
					msg.className = 'error';
					msg.textContent = '註冊失敗';
				}
			});
		});
		fileReader.readAsBinaryString(image.files[0]);
	});

//	const img = document.querySelector('#img');
//	image.addEventListener('change', () => {
//		const file = image.files[0];
//		if (file) {
//			img.src = URL.createObjectURL(file);
//		}
//	});
// })();