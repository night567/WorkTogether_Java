document.addEventListener('DOMContentLoaded', function() {

  // Handle submenu toggling
    var submenuParent = document.querySelector('.submenu > a');
    if (submenuParent) {
      submenuParent.addEventListener('click', function() {
        var submenu = this.nextElementSibling;
        this.parentElement.classList.toggle('active');
        var arrow = this.querySelector('.arrow');
        if (this.parentElement.classList.contains('active')) {
          arrow.classList.remove('fa-chevron-right');
          arrow.classList.add('fa-chevron-down');
        } else {
          arrow.classList.remove('fa-chevron-down');
          arrow.classList.add('fa-chevron-right');
        }
      });
    }
    // Event listener for the back arrow
      document.querySelector('.back-arrow').addEventListener('click', function() {
        window.location.href = '../html/source.html'; // Replace with the correct path if needed
      });

  // a query parameter "menuIds" with a list of ids.
  const params = new URLSearchParams(window.location.search);
  const menuIds = params.get('menuIds').split(',');

  // Now we fetch the menu names using the menuIds
  menuIds.forEach(menuId => {
    fetch('http://localhost:8080/api/menu/company/${menuId}')
      .then(response => response.json())
      .then(data => {
        // Assuming that the menu data returned has a property 'name' that stores the menu name
        addMenuToTable(menuId, data.name);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  });
});

function addMenuToTable(menuId, menuName) {
  const tableBody = document.getElementById('typeTable').querySelector('tbody');
  const row = tableBody.insertRow();
  const cellId = row.insertCell(0);
  const cellName = row.insertCell(1);
  cellId.textContent = menuId;
  cellName.textContent = menuName;
}
