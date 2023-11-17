function drawElement(a) {
  const field = document.getElementById("expression");
  field.value = field.value + a;
}

function deleteElement(flag) {
  const field = document.getElementById("expression");
  (flag) ? field.value = "" : field.value = field.value.slice(0, -1);
}