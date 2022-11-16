import logo from './logo.svg';
import './App.css';
import Sudoku from "./Game/Sudoku";
import SudokuPanel from "./Game/SudokuPanel";
import Generator from "./Game/Generator";

function App() {
  return (
    <div className="App">
        <Sudoku></Sudoku>
        <Generator></Generator>
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
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
