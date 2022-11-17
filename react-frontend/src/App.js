import './App.css';
import Sudoku from "./Game/Sudoku";
import Generator from "./Game/Generator";

function App() {
  return (
    <div className="App">
      <header className="App-header">
          <Sudoku></Sudoku>
          <Generator></Generator>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
