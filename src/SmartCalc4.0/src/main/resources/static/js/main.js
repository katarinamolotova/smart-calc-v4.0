const graphUrl = "http://localhost:8080/graph"

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

function showAlert(flag, message) {
  if (flag) {
    alert(message);
  }
}