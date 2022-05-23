package Shape;

import Controller.Position;
import GUI.Theme;

import static Shape.Direction.*;
import static Shape.Direction.O;

public class Shape_O extends Shape{
    public Shape_O(){
        theme = Theme.theme1;
        blocks = new SingleBlock[4];
        for(int i = 0; i < 4; i ++){
            this.blocks[i] = new SingleBlock();
        }
    }

    @Override
    public void leftRotate(){
        this.extendBlocks();
        return;
    }

    @Override
    public void rightRotate(){
        this.extendBlocks();
        return;
    }

    @Override
    public void extendBlocks(){
        this.blocks[0].position = this.p;
        this.blocks[1].position = this.p.up();
        this.blocks[2].position = this.p.right();
        this.blocks[3].position = this.p.right().up();
    }

    @Override
    public Shape cloneShape(){
        Shape_O result = new Shape_O();
        result.p = new Position(this.p.i, this.p.j);
        result.state = this.state;
        result.blocks = new SingleBlock[this.blocks.length];
        for(int i = 0; i < result.blocks.length; i++){
            result.blocks[i] = new SingleBlock();
        }
        return result;
    }

    @Override
    public void setTheme(Theme theme){
        this.theme = theme;
    }
}
