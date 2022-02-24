getData();

async function getData() {

	  const response = await fetch('http://localhost:8080/showAll',  {
	        method: 'GET',
	        headers: {
	          'Content-Type': 'application/json'
	        }
	        }).then(res => res.json())
	        
	      document.querySelector(".test").innerHTML = "test";
	  }