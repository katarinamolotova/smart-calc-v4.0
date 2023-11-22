const checkbox = document.querySelector('input[name=checkbox]');

checkbox.addEventListener('change', function() {
  const x = document.getElementById("x");
  const maxX = document.getElementById("max-x");
  const minX = document.getElementById("min-x");
  if (this.checked) {
    maxX.style.display = "block"
    minX.style.display = "block"
    x.style.display = "none"
  } else {
    maxX.style.display = "none"
    minX.style.display = "none"
    x.style.display = "block"
  }
});