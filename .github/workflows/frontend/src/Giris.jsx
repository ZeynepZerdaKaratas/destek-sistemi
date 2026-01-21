import React from 'react'
import ReactDOM from 'react-dom/client'
import Uygulama from './Uygulama.jsx' 
import 'bootstrap/dist/css/bootstrap.min.css' // Tasarım kütüphanesi

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Uygulama />
  </React.StrictMode>,
)