import React, {useState} from "react";
import {TextField, InputAdornment} from '@mui/material';
import dmgIcon from '../assets/dmgIcon.png'
import critIcon from '../assets/critIcon.png'
import pierceIcon from '../assets/pierceIcon.png'
import enemyResIcon from '../assets/enemyResIcon.png'
import spellIcon from '../assets/spellIcon.png'

import 'animate.css';


export default function Form() {

    const fields = [
        { text:"Spell Damage", icon: spellIcon },
        { text:"Wizard Damage", icon: dmgIcon },
        { text:"Crit Chance", icon: critIcon },
        { text:"Pierce", icon: pierceIcon },
        { text:"Enemy Resistance", icon: enemyResIcon }
    ]

    // const [pulse, setpulse] =useState("")

    const imgLabel = (imgUrl) => {
        return(
            <div className="imgLabel">
                {/* {txt} */}
                <img src={imgUrl} style={{ height: "30px", overflow: "hidden" }} />
            </div>   
        )
    }

    return (
        <React.Fragment>

                {fields.map((info)=>{
                    return  <TextField 
                    id="filled-basic"
                    variant="filled"
                    label={info.text}
                    // sx={backgroundStyle}
                    InputProps={{startAdornment: <InputAdornment position="start" >{imgLabel(info.icon)}</InputAdornment>}}
                    size="small"
                    margin="dense"
                    value=""
                    />
                })}
        </React.Fragment>
    )
}