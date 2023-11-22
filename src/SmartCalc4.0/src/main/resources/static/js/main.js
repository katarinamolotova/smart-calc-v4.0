function drawElement(a) {
  const field = document.getElementById("expression");
  field.value = field.value + a;
}

function deleteElement(flag) {
  const field = document.getElementById("expression");
  (flag) ? field.value = "" : field.value = field.value.slice(0, -1);
}

function historyElementDbClick(text) {
  const field = document.getElementById("expression");
  field.value = text;
}

const doPostRequest = async (url, data = {}) => {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
  return await response.json();
}