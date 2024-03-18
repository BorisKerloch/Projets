using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Chest : MonoBehaviour
{
    [SerializeField]
    private Color colorChest = Color.red;

    [SerializeField]
    private InventoryController inventoryController;

    private bool chestCanBeOpen = false;

    void Start()
    {
        inventoryController = GetComponent<InventoryController>();
        if(inventoryController == null)
            Debug.LogError("InventoryControllerNotFound");
        UpdateColor();
    }

    // Update is called once per frame
    void Update()
    {
        if (chestCanBeOpen && Input.GetKeyDown(KeyCode.F))
        {
            inventoryController.OpenCloseInventory();
        }
    }

    public void OnTriggerEnter2D(Collider2D other)
    {
        if(other.tag == "Player")
        {
            chestCanBeOpen = true;    
        }

    }

    public void OnTriggerExit2D(Collider2D other)
    {
        if(other.tag == "Player")
        {
            chestCanBeOpen = false;
            if(inventoryController.StatutInventory() == true)
            {
                inventoryController.OpenCloseInventory();
            }
        }
    }

    public Color GetColorChest() {
        return colorChest;
    }

    public void SetColor(Color color) {
        this.colorChest = color;
        UpdateColor();
    }

    public void UpdateColor () {
        SpriteRenderer color = GetComponent<SpriteRenderer>();
        color.color = this.colorChest;
    }

    public int GetIndexBySlot(Slot slot){
        return slot.GetIndex();
    }
}
