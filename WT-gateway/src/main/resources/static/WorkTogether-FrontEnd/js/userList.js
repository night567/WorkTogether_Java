document.addEventListener('DOMContentLoaded', function() {
    // Select the submenu parent link
    var submenuParent = document.querySelector('.submenu > a');
    submenuParent.addEventListener('click', function() {
        // Find the nested submenu
        var submenu = this.nextElementSibling;
        // Toggle the 'active' class on the parent li
        this.parentElement.classList.toggle('active');

        // Rotate the arrow icon
        var arrow = this.querySelector('.arrow');
        if (this.parentElement.classList.contains('active')) {
        arrow.classList.remove('fa-chevron-right');
        arrow.classList.add('fa-chevron-down');
        } else {
        arrow.classList.remove('fa-chevron-down');
        arrow.classList.add('fa-chevron-right');
        }
    });

    // 选择查询按钮
    var queryButton = document.querySelector('.btn-primary');

    // 添加点击事件监听器
    queryButton.addEventListener('click', function () {
        // 获取输入框中的关键字
        var keyword = document.querySelector('.search-box input[type="text"]').value.trim().toLowerCase();
    
        // 获取所有的表格行
        var rows = document.querySelectorAll('tbody tr');
    
        // 遍历每一行
        rows.forEach(function (row) {
            // 获取当前行的角色名称单元格的内容
            var roleName = row.querySelector('td:nth-child(2)').textContent.trim().toLowerCase();
    
            // 如果角色名称包含关键字，则显示该行，否则隐藏该行
            if (roleName.includes(keyword)) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
            });
        });

    //get userList info
    fetch('http://localhost:8080/api/user/company/1', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const tableBody = document.querySelector('table tbody');
            data.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.email}</td>
                    <td>${data.createTime}</td>
                    <td>${data.lastLoginTime}</td>
                    <td><input class="ios-switch" type="checkbox" ${data.status ? 'checked' : ''}></td>
                    <td class="button-container">
                        <button class="assign_roles">分配角色</button>
                        <button class="edit_button">编辑</button>
                        <button class="delete_button">删除</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching data:', error));

    //update user status

    // 为所有复选框添加点击事件监听器
    const checkboxes = document.querySelectorAll('.ios-switch');

    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('click', function() {
            var id = this.parentElement.parentElement.querySelector('td:first-child').textContent;  // 获取所在行的id
            var status = this.checked ? 1 : 0;  // 根据复选框状态设置新的status值

            // 准备要发送的数据
            const newData = {
                id: id,
                status: status
            };

            // 向API发送POST请求
            fetch('http://localhost:8080/api/user/status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newData)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Status updated successfully:', data);
                alert("修改状态成功");
            })
            .catch(error => console.error('Error updating status:', error));
        });
    });

    //get selector option
    // 声明一个空数组用来保存id数据
    let idList = [];
    
    // 发送GET请求获取id和name
    fetch('http://localhost:8080/api/auth', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        // 获取返回的id和name数据
        const id = data.id;
        const name = data.name;

        // 将id保存到idList数组中
        idList.push(id);

        // 查找select元素
        const selectElement = document.getElementById('roles'); // 假设select的id为roles

        // 创建新的option元素
        const newOption = document.createElement('option');
        newOption.value = id;
        newOption.textContent = name;

        // 将旧的option元素替换为新的option元素
        selectElement.innerHTML = ''; // 清空select中的所有option
        selectElement.appendChild(newOption);
    })
    .catch(error => console.error('Error fetching data:', error));

});
(function() {
    selectMultip = {
        register: function(id) {
            //大致思路是：为下拉选创建一个隐藏的子选项，每次单选之后将单选的值追加到隐藏的子选项中，并将子选项选中显示即可
            //全局查找所有标记multip的select
            document.querySelectorAll("[multip]").forEach(function(e) {
                render(e);
            })
        },
        reload: function(id, data, setData) {
            var htm = "";
            for(var i = 0; i < data.length; i++) {
                htm += '<option value="' + data[i].value + '">' + data[i].text + '</option>'
            }
            var e = document.getElementById(id);
            e.innerHTML = htm;
            render(e);
            this.setVal(id, setData);
        },
        setVal: function(id, str) {
            var type = Object.prototype.toString.call(str);
            switch(type) {
                case "[object String]":
                    document.getElementById(id).val = str;
                    break;
                case "[object Array]":
                    document.getElementById(id).val = str.toString();
                    break;
                default:
                    break;
            }
        },
        getVal: function(id) {
            return document.getElementById(id).val;
        },

    }

    function render(e) {
        e.param = {
            arr: [],
            valarr: [],
            opts: []
        };
        var choosevalue = "",
            op;

        for(var i = 0; i < e.length; i++) {
            op = e.item(i);
            e.param.opts.push(op);
            if(op.hasAttribute("choose")) {
                if(choosevalue == "") {
                    choosevalue = op.value
                } else {
                    choosevalue += "," + op.value;
                }

            }
        }

        //创建一个隐藏的option标签用来存储多选的值，其中的值为一个数组
        var option = document.createElement("option");
        option.hidden = true;
        e.appendChild(option);
        e.removeEventListener("input", selchange);
        e.addEventListener("input", selchange);

        //重新定义标签基础属性的get和set方法，实现取值和赋值的功能
        Object.defineProperty(e, "val", {
            get: function() {
                return this.querySelector("[hidden]").value;
            },
            set: function(value) {
                e.param.valarr = [];
                var valrealarr = value == "" ? [] : value.split(",");
                e.param.arr = [];
                e.param.opts.filter(function(o) {
                    o.style = "";
                });
                if(valrealarr.toString()) {
                    for(var i = 0; i < valrealarr.length; i++) {
                        e.param.opts.filter(function(o) {
                            if(o.value == valrealarr[i]) {
                                o.style = "color: blue;";
                                e.param.arr.push(o.text);
                                e.param.valarr.push(o.value)
                            }
                        });
                    }
                    this.options[e.length - 1].text = e.param.arr.toString();
                    this.options[e.length - 1].value = e.param.valarr.toString();
                    this.options[e.length - 1].selected = true;
                } else {
                    this.options[0].selected = true;
                }

            },
            configurable: true
        })
        //添加属性choose 此属性添加到option中用来指定默认值
        e.val = choosevalue;
        //添加属性tip 此属性添加到select标签上
        if(e.hasAttribute("tip") && !e.tiped) {
            e.tiped = true;
            e.insertAdjacentHTML('afterend', '<i style="color: red;font-size: 12px">*可多选</i>');
        }
    }

    function selchange() {
        var text = this.options[this.selectedIndex].text;
        var value = this.options[this.selectedIndex].value;
        this.options[this.selectedIndex].style = "color: blue;";
        var ind = this.param.arr.indexOf(text);
        if(ind > -1) {
            this.param.arr.splice(ind, 1);
            this.param.valarr.splice(ind, 1);
            this.param.opts.filter(function(o) {
                if(o.value == value) {
                    o.style = "";
                }
            });
        } else {
            this.param.arr.push(text);
            this.param.valarr.push(value);
        }
        this.options[this.length - 1].text = this.param.arr.toString();
        this.options[this.length - 1].value = this.param.valarr.toString();
        if(this.param.arr.length > 0) {
            this.options[this.length - 1].selected = true;
        } else {
            this.options[0].selected = true;
        }
    }
})();