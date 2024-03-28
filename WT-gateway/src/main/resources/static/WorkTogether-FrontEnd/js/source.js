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

  // 添加按钮到每一行

    const rows = document.querySelectorAll('table tbody tr');
    rows.forEach(row => {
      const checkBtn = document.createElement('button');
      checkBtn.textContent = '查看详情';
      checkBtn.className = 'checkBtn'; // 你可以在 source.css 中为这个类添加样式

      // 添加查看按钮到行的末尾
      const actionCell = row.cells[row.cells.length - 1]; // 假设操作按钮在每一行的最后一个单元格
      actionCell.innerHTML = ''; // 清空当前内容（如果有的话）
      actionCell.appendChild(checkBtn);

      // 为按钮添加事件监听器（可选）
      checkBtn.addEventListener('click', function() {
        // 在这里添加编辑按钮的点击事件处理逻辑
      });
    });

  // Handle modal functionality
    var modal = document.getElementById('myModal');
    var btn = document.getElementById('addButton');
    var span = document.getElementsByClassName('close')[0];
    var saveBtn = document.getElementById('saveButton');

    // Check if elements exist
    if (btn && modal && span && saveBtn) {
      // When the user clicks the button, open the modal
      btn.onclick = function() {
        modal.style.display = 'block';
      }

      // When the user clicks on <span> (x), close the modal
      span.onclick = function() {
        modal.style.display = 'none';
      }

      // When the user clicks on "保存", close the modal (or handle the save)
      saveBtn.onclick = function() {
        // Implement save logic here
        modal.style.display = 'none';
      }

      // When the user clicks anywhere outside of the modal, close it
      window.onclick = function(event) {
        if (event.target === modal) {
          modal.style.display = 'none';
        }
      }
    } else {
      console.error('One or more modal elements are missing.');
    }

  // 从API获取数据并填充表格
    fetch('http://localhost:8081/resource/selectAllResources')
      .then(response => response.json())
      .then(data => {
        if (data.code === 20041 && Array.isArray(data.data)) {
          const tableBody = document.querySelector('table tbody');
          // 清空现有的表格行
          tableBody.innerHTML = '';
          // 为每个资源创建表格行
          data.data.forEach(resource => {
            const row = tableBody.insertRow();
            row.insertCell().textContent = resource.id;
            row.insertCell().textContent = resource.name;
            row.insertCell().textContent = resource.url;

            const descCell = row.insertCell();
            descCell.textContent = resource.describe;
            descCell.className = 'description-cell'; // 应用省略样式

            const actionCell = row.insertCell();
            const checkBtn = document.createElement('button');
            checkBtn.textContent = '查看详情';
            checkBtn.className = 'checkBtn';
            actionCell.appendChild(checkBtn);

            // 为按钮添加查看详情的点击事件
            checkBtn.addEventListener('click', function() {
              // 更新模态框内容
              document.getElementById('modalResourceId').textContent = resource.id;
              document.getElementById('modalResourceName').textContent = resource.name;
              document.getElementById('modalResourcePath').textContent = resource.url;
              document.getElementById('modalResourceDescription').textContent = resource.describe;
              // 显示模态框
              document.getElementById('checkModal').style.display = 'block';
            });
          });
        }
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
      });

    // 关闭模态框逻辑
    document.querySelectorAll('.modal .close').forEach(function(closeBtn) {
      closeBtn.addEventListener('click', function() {
        this.closest('.modal').style.display = 'none';
      });
    });

    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
      if (event.target.className === 'modal') {
        event.target.style.display = 'none';
      }
    });

    // 当点击资源分类按钮时，跳转到新页面并显示所有资源分类
    document.querySelector('.btn-type').addEventListener('click', () => {
      // 获取资源分类数据
      fetch('http://localhost:8081/resource/selectAllResources')
        .then(response => response.json())
        .then(data => {
          // 资源列表中包含menuId
          const menuIds = data.data.map(resource => resource.menuId);

          // 跳转到新页面并传递menuIds
          window.location.href = `type.html?menuIds=${menuIds.join(',')}`;
        })
        .catch(error => {
          console.error('Error:', error);
        });
    });

    //资源分类下拉表
    document.getElementById('categoryArrow').addEventListener('click', function() {
      // Get the dropdown element
      var categoryDropdown = document.querySelector('.category-dropdown');

      // Fetch the categories from the API
      fetch('http://localhost:8080/api/menu/company/')
        .then(response => response.json())
        .then(data => {
          // Assume that the response is an array of categories
          data.forEach(category => {
            // Create a new option element
            var option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name; // Use the property that has the category name
            // Append the option to the dropdown
            categoryDropdown.appendChild(option);
          });
        })
        .catch(error => {
          console.error('Error fetching categories:', error);
        });

      // Toggle the dropdown visibility
      var dropdownStyle = categoryDropdown.style.display;
      categoryDropdown.style.display = dropdownStyle === 'block' ? 'none' : 'block';
      });

      // Add event listener for the search button
      document.querySelector('.search-btn').addEventListener('click', function() {
        const resourceName = document.getElementById('search-query').value.trim();
        const resourcePath = document.getElementById('search-type').value.trim();
        const resourceCategory = document.getElementById('search-category').value;

        // Fetch and filter the data based on the search criteria
        fetch('http://localhost:8081/resource/selectAllResources')
          .then(response => response.json())
          .then(data => {
            // Filter the data based on the input values
            const filteredData = data.data.filter(resource => {
              return (
                (resourceName === '' || resource.name.includes(resourceName)) &&
                (resourcePath === '' || resource.url.includes(resourcePath)) &&
                (resourceCategory === 'all' || resource.category === resourceCategory)
              );
            });

            // Now display the filtered data
            // Clear the table first
            const tableBody = document.querySelector('table tbody');
            tableBody.innerHTML = '';

            if (filteredData.length > 0) {
              // Create rows for the filtered data
              filteredData.forEach(resource => {
                const row = tableBody.insertRow();
                // ...insert cells and content
              });
            } else {
              // Display 'No data' message
              const row = tableBody.insertRow();
              const cell = row.insertCell(0);
              cell.textContent = '暂无相关数据';
              cell.colSpan = 5; // Span across all columns
            }
          })
          .catch(error => {
            console.error('Error fetching or filtering data:', error);
          });
      });

      // Event listener for the reset button
      document.querySelector('.clear-btn').addEventListener('click', function() {
        console.log('Reset button clicked'); // Check if this message appears in the console
        // Clear the input fields
        document.getElementById('search-query').value = '';
        document.getElementById('search-type').value = '';
        document.getElementById('search-category').value = 'all';
      });

});
