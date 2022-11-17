fetch('/sudoku').then(function(response) {
    return response.json();
}).then(function(data) {

    console.log(data);
    printTable(data);

}).catch(function() {

    console.log("Error!");

});

function printTable(data)
{
    let table = document.getElementById('sudoku');
    let arr = data.array;

    for (let i = 0; i < arr.length; i++)
    {
        let row = document.createElement("tr");
        for (let j = 0; j < arr[i].length; j++)
        {
            let cell = document.createElement("td");
            cell.innerText = arr[i][j];
            row.appendChild(cell);
        }
        table.appendChild(row);
    }
}