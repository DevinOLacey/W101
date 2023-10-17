import logo from './assets/cat-meme.gif';

import './App.css';
import 'animate.css';

//
import { Card } from '@mui/material';

//Imported compenents
import Form from './components/Form';
import Modifiers from './components/Modifiers';

function App() {
  return (
    <div className="App-header ">
      <Card className="form animate__animated animate__bounceInDown" variant="outlined">
        <Form />
        <Modifiers />
      </Card>
    </div>
  );
}

export default App;
