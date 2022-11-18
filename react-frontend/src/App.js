import './App.css';
import Sudoku from "./Game/Sudoku";
import Generator from "./Game/Generator";

function App() {
  return (
    <div className="App">
      <header className="App-header">
          <Sudoku></Sudoku>
          <Generator></Generator>
      </header>
    </div>
  );
}

export default App;
